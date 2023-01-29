package data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.FireAll;

import java.awt.*;
import java.util.Map;

import static data.scripts.vicFM_CharacterDialog.ExampleOptions.*;

public class vicFM_CharacterDialog implements InteractionDialogPlugin {
    public static enum ExampleOptions{
        EXAMPLE_BEGINNING_OPTION,
        test_dialog1,
        test_dialog1_1,
        test_dialog1_2,
        test_dialog2,
        EXAMPLE_END_OPTION,
    }

    protected InteractionDialogAPI dialog;
    protected TextPanelAPI textPanel;
    protected OptionPanelAPI options;
    protected VisualPanelAPI visual;

    protected CampaignFleetAPI playerFleet;
    protected PersonAPI person;

    public void init(InteractionDialogAPI dialog) {
        this.dialog = dialog;
        textPanel = dialog.getTextPanel();
        options = dialog.getOptionPanel();
        visual = dialog.getVisualPanel();

        playerFleet = Global.getSector().getPlayerFleet();

        person=Global.getSector().getFaction("vic").createRandomPerson();

        optionSelected(null, EXAMPLE_BEGINNING_OPTION);
    }

    public Map<String, MemoryAPI> getMemoryMap() {
        return null;
    }

    public void backFromEngagement(EngagementResultAPI result) {

    }

    public void optionSelected(String text, Object optionData) {
        if (optionData == null) return;

        ExampleOptions option = (ExampleOptions) optionData;

        if (text != null) {
            textPanel.addParagraph(text, Global.getSettings().getColor("buttonText"));
        }

        Color vic = Global.getSector().getFaction("vic").getBaseUIColor();

        switch (option) {
            case EXAMPLE_BEGINNING_OPTION:
                visual.showPersonInfo(person, true);
                options.addOption("Option 1.", test_dialog1, null);
                options.addOption("Option 2.", test_dialog2, null);
                break;
            case test_dialog1:
                options.clearOptions();
                textPanel.addParagraph("I believe we live in a society.");
                options.addOption("Option 1_1.", test_dialog1_1, null);
                options.addOption("Option 1_2.", test_dialog1_2, null);
                break;
            case test_dialog1_1:
                options.clearOptions();
                textPanel.addParagraph("Option 1_1 text.");
                options.addOption("\"Bye\"", EXAMPLE_END_OPTION, null);
                break;

            case test_dialog1_2:
                options.clearOptions();
                textPanel.addParagraph("Option 1_2 text.");
                options.addOption("\"Bye\"", EXAMPLE_END_OPTION, "test tooltip for 1_2");
                break;

            case test_dialog2:
                options.clearOptions();
                textPanel.addParagraph("Option 2 text.");
                options.addOption("\"Bye\"", EXAMPLE_END_OPTION, vic,"test tooltip for 1_2");
                break;

            case EXAMPLE_END_OPTION:
                if(Global.getSector().getPersistentData().get("seven_originaldialog")!=null) {
                    InteractionDialogPlugin original = (InteractionDialogPlugin) Global.getSector().getPersistentData().get("seven_originaldialog");
                    dialog.setPlugin(original);
                    options.clearOptions();
                    FireAll.fire(null, dialog, original.getMemoryMap(), "PopulateOptions");
                    Global.getSector().getPersistentData().remove("seven_originaldialog");
                }else{
                    dialog.dismiss();
                }
                break;
        }
    }




    public void optionMousedOver(String optionText, Object optionData) {

    }

    public void advance(float amount) {

    }

    public Object getContext() {
        return null;
    }


}

package data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;

import java.awt.*;
import java.util.Map;

import static data.scripts.vicFM_CharacterDialog.OptionID_variable_name.*;

public class vicFM_CharacterDialog implements InteractionDialogPlugin {
    public static enum OptionID_variable_name {
        EXAMPLE_BEGINNING_OPTION,
        EXAMPLE_OPTION1,
        EXAMPLE_OPTION2,
        EXAMPLE_OPTION3,
        EXAMPLE_EXIT_OPTION,
    }

    protected InteractionDialogAPI dialog;
    protected TextPanelAPI textPanel;
    protected OptionPanelAPI options;
    protected VisualPanelAPI visual;

    protected CampaignFleetAPI playerFleet;

    public void init(InteractionDialogAPI dialog) {
        this.dialog = dialog;
        textPanel = dialog.getTextPanel();
        options = dialog.getOptionPanel();
        visual = dialog.getVisualPanel();

        playerFleet = Global.getSector().getPlayerFleet();

        optionSelected(null, EXAMPLE_BEGINNING_OPTION);
    }

    public Map<String, MemoryAPI> getMemoryMap() {
        return null;
    }

    public void backFromEngagement(EngagementResultAPI result) {

    }

    public void optionSelected(String text, Object optionData) {
        if (optionData == null) return;

        OptionID_variable_name option = (OptionID_variable_name) optionData;

        if (text != null) {
            textPanel.addParagraph(text, Global.getSettings().getColor("buttonText"));
        }

        Color vic = Global.getSector().getFaction("vic").getBaseUIColor();

        switch (option) {
            case EXAMPLE_BEGINNING_OPTION:
                textPanel.addParagraph("Example paragraph text " +
                        "\"example text that appears between quotation marks.\"");

                options.clearOptions();
                options.addOption("\"Example option text 1.\"", EXAMPLE_OPTION1, null);
                options.addOption("\"Example option text 2.\"", EXAMPLE_OPTION2, "This one has a tooltip.");
                options.addOption("\"Example option text 3.\"", EXAMPLE_OPTION3, vic,"This one has a tooltip.");

                break;
            case EXAMPLE_OPTION1:
                textPanel.addParagraph("The other options have not been cleared here.");
                break;
            case EXAMPLE_OPTION2:
                dialog.dismiss();
                break;
            case EXAMPLE_OPTION3:
                dialog.dismiss();
                break;
            case EXAMPLE_EXIT_OPTION:
                //Global.getSector().setPaused(false);
                dialog.dismiss();
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

}

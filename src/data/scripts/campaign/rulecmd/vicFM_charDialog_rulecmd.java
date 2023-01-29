package data.scripts.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.InteractionDialogPlugin;
import com.fs.starfarer.api.campaign.OptionPanelAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;
import data.scripts.vicFM_CharacterDialog;

import java.util.List;
import java.util.Map;

public class vicFM_charDialog_rulecmd extends BaseCommandPlugin {

    @Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        OptionPanelAPI options = dialog.getOptionPanel();
        options.clearOptions();

        String cmd = params.get(0).getString(memoryMap);

        Global.getSector().getPersistentData().put("$vicFM_exposition",dialog.getPlugin());

        //nuke: some refactors for neatness and performance, done with Lukas04's help

        InteractionDialogPlugin characterDialog=null;

        //nuke: put a new case for every new character you add. Follow the format in rules.csv to see the name of the case. characterDialog should be set equal to the name of the InteractionDialogPlugin class you have defined.
        switch (cmd) {
            case "vic_exposition_character":
                characterDialog= new vicFM_CharacterDialog();
                break;
        }

        dialog.setPlugin(characterDialog);
        characterDialog.init(dialog);

        return true;
    }
}

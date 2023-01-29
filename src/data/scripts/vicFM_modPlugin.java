package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ModManagerAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.characters.PersonAPI;
import exerelin.campaign.SectorManager;

import java.util.Map;

public class vicFM_modPlugin extends BaseModPlugin {
    boolean haveNexerelin;
    Map<String, Object> data;


    public void onGameLoad(boolean wasEnabledBefore) {
        SectorAPI sector=Global.getSector();
        data= sector.getPersistentData();
        haveNexerelin= Global.getSettings().getModManager().isModEnabled("nexerelin");
        if(data.containsKey("$vicGenerated")){
            if (!data.containsKey("vic_characters_added")&&!haveNexerelin || SectorManager.getManager().isCorvusMode()) {
                MarketAPI plegMarket=sector.getStarSystem("Empyrean").getEntityById("vic_planet_phlegethon").getMarket();
                FactionAPI plegFaction=plegMarket.getFaction();
                if(plegFaction.getId()=="vic"){
                    data.put("vic_characters_added",true);
                    PersonAPI expositionPerson = plegFaction.createRandomPerson();
                    expositionPerson.getName().setFirst("TEST PERSON FIRST NAME");
                    expositionPerson.getName().setLast("TEST PERSON LAST NAME");
                    expositionPerson.setId("vic_exposition_character");
                    //nuke: can also implement as a bar event instead of as a comm directory person
                    plegMarket.getCommDirectory().addPerson(expositionPerson);
                    if(!Global.getSector().getImportantPeople().containsPerson(expositionPerson)) Global.getSector().getImportantPeople().addPerson(expositionPerson);
                }
            }
        }

    }
}

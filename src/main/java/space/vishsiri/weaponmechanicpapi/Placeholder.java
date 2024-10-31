package space.vishsiri.weaponmechanicpapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.deecaad.weaponmechanics.WeaponMechanicsAPI;
import me.deecaad.weaponmechanics.utils.CustomTag;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Placeholder extends PlaceholderExpansion {

    private final WeaponMechanicPAPI plugin;

    public Placeholder(WeaponMechanicPAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    @NotNull
    public String getAuthor() {
        return "VisherRyz";
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "wpm";
    }

    @Override
    @NotNull
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getRequiredPlugin() {
        return "WeaponMechanics";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "none";
        }

        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        if (mainHand.getType().isAir()) {
            mainHand = null;
        }

        if (offHand.getType().isAir()) {
            offHand = null;
        }

        String result = "none";

        switch (identifier) {
            case "current_weapon_title": {
                boolean mainHandIsWeapon = mainHand != null && CustomTag.WEAPON_TITLE.hasString(mainHand);
                boolean offHandIsWeapon = offHand != null && CustomTag.WEAPON_TITLE.hasString(offHand);

                if (mainHandIsWeapon) {
                    result = CustomTag.WEAPON_TITLE.getString(mainHand);
                } else if (offHandIsWeapon) {
                    result = CustomTag.WEAPON_TITLE.getString(offHand);
                } else {
                    result = "";
                }
                break;
            }
            case "current_weapon_ammo": {
                boolean mainHandIsWeapon = mainHand != null && CustomTag.WEAPON_TITLE.hasString(mainHand);
                boolean offHandIsWeapon = offHand != null && CustomTag.WEAPON_TITLE.hasString(offHand);

                if (mainHandIsWeapon) {
                    result = String.valueOf(CustomTag.AMMO_LEFT.getInteger(mainHand));
                } else if (offHandIsWeapon) {
                    result = String.valueOf(CustomTag.AMMO_LEFT.getInteger(offHand));
                } else {
                    result = "";
                }
                break;
            }
            case "current_weapon_ammo_type": {
                boolean mainHandIsWeapon = mainHand != null && CustomTag.WEAPON_TITLE.hasString(mainHand);
                boolean offHandIsWeapon = offHand != null && CustomTag.WEAPON_TITLE.hasString(offHand);

                if (mainHandIsWeapon) {
                    result = "1";
                } else if (offHandIsWeapon) {
                    result = "1";
                } else {
                    result = "";
                }
                break;
            }
            case "current_weapon_durability": {
                boolean mainHandIsWeapon = mainHand != null && CustomTag.WEAPON_TITLE.hasString(mainHand);
                boolean offHandIsWeapon = offHand != null && CustomTag.WEAPON_TITLE.hasString(offHand);
                if (mainHandIsWeapon || offHandIsWeapon)  {
                    Integer durability = CustomTag.DURABILITY.getInteger((mainHand != null ? mainHand : offHand));
                    result = durability + "%";
                }
                else {
                    result = "";
                }
                break;
            }
            case "current_weapon_selective":
                boolean mainHandIsWeapon = mainHand != null && CustomTag.WEAPON_TITLE.hasString(mainHand);
                boolean offHandIsWeapon = offHand != null && CustomTag.WEAPON_TITLE.hasString(offHand);
                if (mainHandIsWeapon || offHandIsWeapon)  {
                    int mainHandValue = CustomTag.SELECTIVE_FIRE.getInteger((mainHand != null ? mainHand : offHand));
                    result = getSelectiveFireOutput(mainHandValue);
                }
                else {
                    result = "";
                }
                break;
            case "current_weapon":
                boolean mainHandIsWeapon1 = mainHand != null && CustomTag.WEAPON_TITLE.hasString(mainHand);
                boolean offHandIsWeapon1 = offHand != null && CustomTag.WEAPON_TITLE.hasString(offHand);
                result = (mainHandIsWeapon1 || offHandIsWeapon1) ? "1" : "0";
                break;
        }

        return result != null ? result : "";
    }

    private String getSelectiveFireOutput(int value) {
        String path = "placeholder_output.selective_fire." + value + ".output";
        return plugin.getConfig().getString(path, "UNKNOWN"); // Default to "UNKNOWN" if not found
    }

}

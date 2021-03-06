package joshie.mariculture.modules.fishery.rod;

import joshie.mariculture.api.fishing.Fishing.Size;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FishingRod {
    public static final FishingRod NULL_FISHING_ROD = new FishingRod();
    private RodPole pole;
    private RodReel reel;
    private RodString string;
    private RodHook hook;
    private String unlocalized;
    private boolean isCast;
    private boolean invulnerable;
    private int strength;

    private FishingRod(){}
    public FishingRod(RodPole pole, RodReel reel, RodString string, RodHook hook) {
        this.pole = pole;
        this.reel = reel;
        this.string = string;
        this.hook = hook;
        this.unlocalized = pole.getResource().getResourceDomain() + ".item.fishingrod." + pole.getResource().getResourcePath().replace("_", "");
        this.isCast = false;
        this.invulnerable = false;
        this.strength = (int)(pole.getStrength() * string.getStrengthModifier());
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked, deprecation")
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocal(unlocalized);
    }

    /** Call this to make this fishing rod type invulnerable **/
    @SuppressWarnings("unused")
    public FishingRod setInvulnerable() {
        this.invulnerable = true;
        return this;
    }

    /** Set this cast status **/
    public void setCastStatus(boolean castStatus) {
        this.isCast = castStatus;
    }

    /** Returns if this is invulnerable to damage **/
    public boolean isInvulnerable() {
        return invulnerable;
    }

    /** Returns the strength of this fishing rod **/
    public int getStrength() {
        return strength;
    }

    /** Returns the durability of this fishing rod **/
    public int getDurability() {
        return pole.getDurability();
    }

    /** Returns the height modifier **/
    public float getHeightModifier() {
        return reel.getHeightModifier();
    }

    /** Returns the distance bonus **/
    public float getDistanceBonus() {
        return reel.getDistanceBonus();
    }

    /** Returns the retract speed **/
    public double getRetractSpeed() {
        return reel.getRetractSpeed();
    }

    /** Returns the catch speed **/
    public int getCatchSpeed() {
        return hook.getCatchSpeed();
    }

    /** Returns the size this rod catches best **/
    public Size[] getBestSizes() {
        return hook.getBestSize();
    }

    /** Return true if this rod is in cast mode **/
    public boolean isCast() {
        return isCast;
    }

    /** Returns the pole **/
    public RodPole getPole() {
        return pole;
    }

    /** Returns the reel **/
    public RodReel getReel() {
        return reel;
    }

    /** Returns the string **/
    public RodString getString() {
        return string;
    }

    /** Returns the hook **/
    public RodHook getHook() {
        return hook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FishingRod that = (FishingRod) o;
        if (isCast != that.isCast) return false;
        if (pole != null ? !pole.equals(that.pole) : that.pole != null) return false;
        if (reel != null ? !reel.equals(that.reel) : that.reel != null) return false;
        if (string != null ? !string.equals(that.string) : that.string != null) return false;
        return hook != null ? hook.equals(that.hook) : that.hook == null;

    }

    @Override
    public int hashCode() {
        int result = pole != null ? pole.hashCode() : 0;
        result = 31 * result + (reel != null ? reel.hashCode() : 0);
        result = 31 * result + (string != null ? string.hashCode() : 0);
        result = 31 * result + (hook != null ? hook.hashCode() : 0);
        result = 31 * result + (isCast ? 1 : 0);
        return result;
    }
}

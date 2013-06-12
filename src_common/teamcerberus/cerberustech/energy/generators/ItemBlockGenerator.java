package teamcerberus.cerberustech.energy.generators;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockGenerator extends ItemBlock {

	public ItemBlockGenerator(int id) {
		super(id);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return GeneratorType.values()[itemstack.getItemDamage()]
				.getLocalizedName();
	}

}

package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.blocks.base.TileMultiBlock.MultiPart;
import mariculture.diving.TileAirCompressor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class Packet118InitAirCompressor extends PacketMariculture {
	
	public int x, y, z, mX, mY, mZ, facing;
	
	public Packet118InitAirCompressor() {}
	
	public Packet118InitAirCompressor(int x, int y, int z, int mX, int mY, int mZ, ForgeDirection facing) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.mX = mX;
		this.mY = mY;
		this.mZ = mZ;
		this.facing = facing.ordinal();
	}

	@Override
	public void handle(World world, EntityPlayer player) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof TileAirCompressor) {
			((TileAirCompressor) te).setMaster(new MultiPart(mX, mY, mZ));
			((TileAirCompressor) te).setFacing(ForgeDirection.values()[facing]);
			world.markBlockForRenderUpdate(x, y, z);
		}
	}

	@Override
	public void read(DataInputStream is) throws IOException {
		x = is.readInt();
		y = is.readInt();
		z = is.readInt();
		mX = is.readInt();
		mY = is.readInt();
		mZ = is.readInt();
		facing = is.readInt();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(x);
		os.writeInt(y);
		os.writeInt(z);
		os.writeInt(mX);
		os.writeInt(mY);
		os.writeInt(mZ);
		os.writeInt(facing);
	}

}
package tk.simplexclient.ui.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import java.io.File;

public class FakeSaveHandler implements ISaveHandler {

    @Override
    public WorldInfo loadWorldInfo() {
        return null;
    }

    @Override
    public void checkSessionLock() throws MinecraftException {

    }

    @Override
    public IChunkLoader getChunkLoader(WorldProvider provider) {
        return null;
    }

    @Override
    public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {

    }

    @Override
    public void saveWorldInfo(WorldInfo worldInformation) {

    }

    @Override
    public IPlayerFileData getPlayerNBTManager() {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public File getWorldDirectory() {
        return null;
    }

    @Override
    public File getMapFileFromName(String mapName) {
        return null;
    }

    @Override
    public String getWorldDirectoryName() {
        return "";
    }
}
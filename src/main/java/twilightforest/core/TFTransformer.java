package twilightforest.core;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class TFTransformer implements IClassTransformer, Opcodes {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        switch (transformedName) {
            case "net.minecraft.entity.EntityLiving": return this.transformEntityLiving(basicClass);
            default: return basicClass;
        }
    }

    private byte[] transformEntityLiving(byte[] basicClass) {
        ClassReader reader = new ClassReader(basicClass);
        ClassNode cls = new ClassNode();
        reader.accept(cls, 0);
        for (MethodNode method : cls.methods) {
            if (method.name.equals(FMLLaunchHandler.isDeobfuscatedEnvironment() ? "despawnEntity" : "func_70623_bb")) {
                AbstractInsnNode node = method.instructions.getLast();
                while (node.getOpcode() != RETURN) node = node.getPrevious();
                InsnList list = new InsnList();
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new MethodInsnNode(INVOKESTATIC, "twilightforest/core/TFHooks", "handleBossDespawning", "(Lnet/minecraft/entity/EntityLiving;)V", false));
                method.instructions.insertBefore(node, list);
                break;
            }
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cls.accept(writer);
        return writer.toByteArray();
    }
}

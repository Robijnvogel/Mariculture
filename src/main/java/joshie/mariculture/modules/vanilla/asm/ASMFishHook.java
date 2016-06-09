package joshie.mariculture.modules.vanilla.asm;

import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

/** Adds player context when fishing, so I can use the vanilla fishing system instead of my own
 *  Adds the lines > lootcontext$builder.withPlayer(this.angler);
 *                 > lootcontext$builder.withLootedEntity(this);
 *  After: lootcontext$builder.withLuck((float) EnchantmentHelper.getLuckOfSeaModifier(this.angler) + this.angler.getLuck());
 *  in net.minecraft.entity.projectile.EntityFishHook
  **/
public class ASMFishHook extends AbstractASM {
    @Override
    public boolean isClass(String name) {
        return name.equals("net.minecraft.entity.projectile.EntityFishHook") || name.equals("xw");
    }

    @Override
    public ClassVisitor newInstance(ClassWriter writer) {
        return new ASMVisitor(writer);
    }

    public class ASMVisitor extends ClassVisitor {
        public ASMVisitor(ClassWriter writer) {
            super(Opcodes.ASM4, writer);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor visitor = super.visitMethod(access, name, desc, signature, exceptions);
            if (desc.equals("()I") && (name.equals("handleHookRetraction") || name.equals("func_146034_e") || name.equals("j"))) {
                return new MethodVisitor(Opcodes.ASM4, visitor) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                        super.visitMethodInsn(opcode, owner, name, desc, itf);
                        if (name.equals("withLuck") || name.equals("func_186469_a")|| (name.equals("a") && desc.equals("(F)Lazx$a;"))) {
                            String angler = name.equals("withLuck") ? "angler" : "field_146042_b";
                            String withPlayer = name.equals("withLuck") ? "withPlayer" : "func_186470_a";
                            String withLootedEntity = name.equals("withLuck") ? "withLootedEntity" : "func_186472_a" ;
                            mv.visitInsn(POP);
                            Label l2 = new Label();
                            mv.visitLabel(l2);
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitFieldInsn(GETFIELD, "net/minecraft/entity/projectile/EntityFishHook", angler, "Lnet/minecraft/entity/player/EntityPlayer;");
                            mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/storage/loot/LootContext$Builder", withPlayer, "(Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/world/storage/loot/LootContext$Builder;", false);
                            mv.visitInsn(POP);
                            Label l3 = new Label();
                            mv.visitLabel(l3);
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitVarInsn(ALOAD, 0);
                            mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/storage/loot/LootContext$Builder", withLootedEntity, "(Lnet/minecraft/entity/Entity;)Lnet/minecraft/world/storage/loot/LootContext$Builder;", false);
                        }
                    }
                };
            }

            return visitor;
        }
    }
}

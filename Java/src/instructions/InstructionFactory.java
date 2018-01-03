package instructions;

import instructions.base.Instruction;
import instructions.comparisons.dcmp.DCMPG;
import instructions.comparisons.dcmp.DCMPL;
import instructions.comparisons.fcmp.FCMPG;
import instructions.comparisons.fcmp.FCMPL;
import instructions.comparisons.ifacmp.IF_ACMPEQ;
import instructions.comparisons.ifacmp.IF_ACMPNE;
import instructions.comparisons.ifcond.*;
import instructions.comparisons.ificmp.*;
import instructions.comparisons.lcmp.LCMP;
import instructions.constants.*;
import instructions.control.*;
import instructions.conversions.d2x.D2F;
import instructions.conversions.d2x.D2I;
import instructions.conversions.d2x.D2L;
import instructions.conversions.f2x.F2D;
import instructions.conversions.f2x.F2I;
import instructions.conversions.f2x.F2L;
import instructions.conversions.i2x.*;
import instructions.conversions.l2x.L2D;
import instructions.conversions.l2x.L2F;
import instructions.conversions.l2x.L2I;
import instructions.extended.GOTO_W;
import instructions.extended.IFNONNULL;
import instructions.extended.IFNULL;
import instructions.extended.WIDE;
import instructions.loads.loaddouble.*;
import instructions.loads.loadfloat.*;
import instructions.loads.loadint.*;
import instructions.loads.loadlong.*;
import instructions.loads.loadref.*;
import instructions.loads.loadxarr.*;
import instructions.math.add.DADD;
import instructions.math.add.FADD;
import instructions.math.add.IADD;
import instructions.math.add.LADD;
import instructions.math.and.IAND;
import instructions.math.and.LAND;
import instructions.math.div.DDIV;
import instructions.math.div.FDIV;
import instructions.math.div.IDIV;
import instructions.math.div.LDIV;
import instructions.math.iinc.IINC;
import instructions.math.mul.DMUL;
import instructions.math.mul.FMUL;
import instructions.math.mul.IMUL;
import instructions.math.mul.LMUL;
import instructions.math.neg.DNEG;
import instructions.math.neg.FNEG;
import instructions.math.neg.INEG;
import instructions.math.neg.LNEG;
import instructions.math.or.IOR;
import instructions.math.or.LOR;
import instructions.math.rem.DREM;
import instructions.math.rem.FREM;
import instructions.math.rem.IREM;
import instructions.math.rem.LREM;
import instructions.math.sh.*;
import instructions.math.sub.DSUB;
import instructions.math.sub.FSUB;
import instructions.math.sub.ISUB;
import instructions.math.sub.LSUB;
import instructions.math.xor.IXOR;
import instructions.math.xor.LXOR;
import instructions.references.*;
import instructions.stack.dup.*;
import instructions.stack.pop.POP;
import instructions.stack.pop.POP2;
import instructions.stack.swap.SWAP;
import instructions.stores.storedouble.*;
import instructions.stores.storefloat.*;
import instructions.stores.storeint.*;
import instructions.stores.storelong.*;
import instructions.stores.storeref.*;
import instructions.stores.storexarr.*;


/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class InstructionFactory {
    static NOP nop = new NOP();
    static ACONST_NULL aconst_null = new ACONST_NULL();
    static ICONST_M1 iconst_m1 = new ICONST_M1();
    static ICONST_0 iconst_0 = new ICONST_0();
    static ICONST_1 iconst_1 = new ICONST_1();
    static ICONST_2 iconst_2 = new ICONST_2();
    static ICONST_3 iconst_3 = new ICONST_3();
    static ICONST_4 iconst_4 = new ICONST_4();
    static ICONST_5 iconst_5 = new ICONST_5();
    static LCONST_0 lconst_0 = new LCONST_0();
    static LCONST_1 lconst_1 = new LCONST_1();
    static FCONST_0 fconst_0 = new FCONST_0();
    static FCONST_1 fconst_1 = new FCONST_1();
    static FCONST_2 fconst_2 = new FCONST_2();
    static DCONST_0 dconst_0 = new DCONST_0();
    static DCONST_1 dconst_1 = new DCONST_1();
    static ILOAD_0 iload_0 = new ILOAD_0();
    static ILOAD_1 iload_1 = new ILOAD_1();
    static ILOAD_2 iload_2 = new ILOAD_2();
    static ILOAD_3 iload_3 = new ILOAD_3();
    static LLOAD_0 lload_0 = new LLOAD_0();
    static LLOAD_1 lload_1 = new LLOAD_1();
    static LLOAD_2 lload_2 = new LLOAD_2();
    static LLOAD_3 lload_3 = new LLOAD_3();
    static FLOAD_0 fload_0 = new FLOAD_0();
    static FLOAD_1 fload_1 = new FLOAD_1();
    static FLOAD_2 fload_2 = new FLOAD_2();
    static FLOAD_3 fload_3 = new FLOAD_3();
    static DLOAD_0 dload_0 = new DLOAD_0();
    static DLOAD_1 dload_1 = new DLOAD_1();
    static DLOAD_2 dload_2 = new DLOAD_2();
    static DLOAD_3 dload_3 = new DLOAD_3();
    static ALOAD_0 aload_0 = new ALOAD_0();
    static ALOAD_1 aload_1 = new ALOAD_1();
    static ALOAD_2 aload_2 = new ALOAD_2();
    static ALOAD_3 aload_3 = new ALOAD_3();
    static IALOAD iaload = new IALOAD();
    static LALOAD laload = new LALOAD();
    static FALOAD faload = new FALOAD();
    static DALOAD daload = new DALOAD();
    static AALOAD aaload = new AALOAD();
    static BALOAD baload = new BALOAD();
    static CALOAD caload = new CALOAD();
    static SALOAD saload = new SALOAD();
    static ISTORE_0 istore_0 = new ISTORE_0();
    static ISTORE_1 istore_1 = new ISTORE_1();
    static ISTORE_2 istore_2 = new ISTORE_2();
    static ISTORE_3 istore_3 = new ISTORE_3();
    static LSTORE_0 lstore_0 = new LSTORE_0();
    static LSTORE_1 lstore_1 = new LSTORE_1();
    static LSTORE_2 lstore_2 = new LSTORE_2();
    static LSTORE_3 lstore_3 = new LSTORE_3();
    static FSTORE_0 fstore_0 = new FSTORE_0();
    static FSTORE_1 fstore_1 = new FSTORE_1();
    static FSTORE_2 fstore_2 = new FSTORE_2();
    static FSTORE_3 fstore_3 = new FSTORE_3();
    static DSTORE_0 dstore_0 = new DSTORE_0();
    static DSTORE_1 dstore_1 = new DSTORE_1();
    static DSTORE_2 dstore_2 = new DSTORE_2();
    static DSTORE_3 dstore_3 = new DSTORE_3();
    static ASTORE_0 astore_0 = new ASTORE_0();
    static ASTORE_1 astore_1 = new ASTORE_1();
    static ASTORE_2 astore_2 = new ASTORE_2();
    static ASTORE_3 astore_3 = new ASTORE_3();
    static IASTORE iastore = new IASTORE();
    static LASTORE lastore = new LASTORE();
    static FASTORE fastore = new FASTORE();
    static DASTORE dastore = new DASTORE();
    static AASTORE aastore = new AASTORE();
    static BASTORE bastore = new BASTORE();
    static CASTORE castore = new CASTORE();
    static SASTORE sastore = new SASTORE();
    static POP pop = new POP();
    static POP2 pop2 = new POP2();
    static DUP dup = new DUP();
    static DUP_X1 dup_x1 = new DUP_X1();
    static DUP_X2 dup_x2 = new DUP_X2();
    static DUP2 dup2 = new DUP2();
    static DUP2_X1 dup2_x1 = new DUP2_X1();
    static DUP2_X2 dup2_x2 = new DUP2_X2();
    static SWAP swap = new SWAP();
    static IADD iadd = new IADD();
    static LADD ladd = new LADD();
    static FADD fadd = new FADD();
    static DADD dadd = new DADD();
    static ISUB isub = new ISUB();
    static LSUB lsub = new LSUB();
    static FSUB fsub = new FSUB();
    static DSUB dsub = new DSUB();
    static IMUL imul = new IMUL();
    static LMUL lmul = new LMUL();
    static FMUL fmul = new FMUL();
    static DMUL dmul = new DMUL();
    static IDIV idiv = new IDIV();
    static LDIV ldiv = new LDIV();
    static FDIV fdiv = new FDIV();
    static DDIV ddiv = new DDIV();
    static IREM irem = new IREM();
    static LREM lrem = new LREM();
    static FREM frem = new FREM();
    static DREM drem = new DREM();
    static INEG ineg = new INEG();
    static LNEG lneg = new LNEG();
    static FNEG fneg = new FNEG();
    static DNEG dneg = new DNEG();
    static ISHL ishl = new ISHL();
    static LSHL lshl = new LSHL();
    static ISHR ishr = new ISHR();
    static LSHR lshr = new LSHR();
    static IUSHR iushr = new IUSHR();
    static LUSHR lushr = new LUSHR();
    static IAND iand = new IAND();
    static LAND land = new LAND();
    static IOR ior = new IOR();
    static LOR lor = new LOR();
    static IXOR ixor = new IXOR();
    static LXOR lxor = new LXOR();
    static I2L i2l = new I2L();
    static I2F i2f = new I2F();
    static I2D i2d = new I2D();
    static L2I l2i = new L2I();
    static L2F l2f = new L2F();
    static L2D l2d = new L2D();
    static F2I f2i = new F2I();
    static F2L f2l = new F2L();
    static F2D f2d = new F2D();
    static D2I d2i = new D2I();
    static D2L d2l = new D2L();
    static D2F d2f = new D2F();
    static I2B i2b = new I2B();
    static I2C i2c = new I2C();
    static I2S i2s = new I2S();
    static LCMP lcmp = new LCMP();
    static FCMPL fcmpl = new FCMPL();
    static FCMPG fcmpg = new FCMPG();
    static DCMPL dcmpl = new DCMPL();
    static DCMPG dcmpg = new DCMPG();
    static IRETURN ireturn = new IRETURN();
    static LRETURN lreturn = new LRETURN();
    static FRETURN freturn = new FRETURN();
    static DRETURN dreturn = new DRETURN();
    static ARETURN areturn = new ARETURN();
    static RETURN _return = new RETURN();
    static ARRAY_LENGTH arraylength = new ARRAY_LENGTH();
    static ATHROW athrow = new ATHROW();
    /* static MONITOR_ENTER monitorenter  = new MONITOR_ENTER();
   static MONITOR_EXIT monitorexit   = new MONITOR_EXIT(); */
    static INVOKE_NATIVE invoke_native = new INVOKE_NATIVE();


    public static Instruction createInstruction(int opCode) {
        switch (opCode) {
            case 0x00:
                return nop;
            case 0x01:
                return aconst_null;
            case 0x02:
                return iconst_m1;
            case 0x03:
                return iconst_0;
            case 0x04:
                return iconst_1;
            case 0x05:
                return iconst_2;
            case 0x06:
                return iconst_3;
            case 0x07:
                return iconst_4;
            case 0x08:
                return iconst_5;
            case 0x09:
                return lconst_0;
            case 0x0a:
                return lconst_1;
            case 0x0b:
                return fconst_0;
            case 0x0c:
                return fconst_1;
            case 0x0d:
                return fconst_2;
            case 0x0e:
                return dconst_0;
            case 0x0f:
                return dconst_1;
            case 0x10:
                return new BIPUSH();
            case 0x11:
                return new SIPUSH();
            case 0x12:
                return new LDC();
            case 0x13:
                return new LDC_W();
            case 0x14:
                return new LDC2_W();
            case 0x15:
                return new ILOAD();
            case 0x16:
                return new LLOAD();
            case 0x17:
                return new FLOAD();
            case 0x18:
                return new DLOAD();
            case 0x19:
                return new ALOAD();
            case 0x1a:
                return iload_0;
            case 0x1b:
                return iload_1;
            case 0x1c:
                return iload_2;
            case 0x1d:
                return iload_3;
            case 0x1e:
                return lload_0;
            case 0x1f:
                return lload_1;
            case 0x20:
                return lload_2;
            case 0x21:
                return lload_3;
            case 0x22:
                return fload_0;
            case 0x23:
                return fload_1;
            case 0x24:
                return fload_2;
            case 0x25:
                return fload_3;
            case 0x26:
                return dload_0;
            case 0x27:
                return dload_1;
            case 0x28:
                return dload_2;
            case 0x29:
                return dload_3;
            case 0x2a:
                return aload_0;
            case 0x2b:
                return aload_1;
            case 0x2c:
                return aload_2;
            case 0x2d:
                return aload_3;
            case 0x2e:
                return iaload;
            case 0x2f:
                return laload;
            case 0x30:
                return faload;
            case 0x31:
                return daload;
            case 0x32:
                return aaload;
            case 0x33:
                return baload;
            case 0x34:
                return caload;
            case 0x35:
                return saload;
            case 0x36:
                return new ISTORE();
            case 0x37:
                return new LSTORE();
            case 0x38:
                return new FSTORE();
            case 0x39:
                return new DSTORE();
            case 0x3a:
                return new ASTORE();
            case 0x3b:
                return istore_0;
            case 0x3c:
                return istore_1;
            case 0x3d:
                return istore_2;
            case 0x3e:
                return istore_3;
            case 0x3f:
                return lstore_0;
            case 0x40:
                return lstore_1;
            case 0x41:
                return lstore_2;
            case 0x42:
                return lstore_3;
            case 0x43:
                return fstore_0;
            case 0x44:
                return fstore_1;
            case 0x45:
                return fstore_2;
            case 0x46:
                return fstore_3;
            case 0x47:
                return dstore_0;
            case 0x48:
                return dstore_1;
            case 0x49:
                return dstore_2;
            case 0x4a:
                return dstore_3;
            case 0x4b:
                return astore_0;
            case 0x4c:
                return astore_1;
            case 0x4d:
                return astore_2;
            case 0x4e:
                return astore_3;
            case 0x4f:
                return iastore;
            case 0x50:
                return lastore;
            case 0x51:
                return fastore;
            case 0x52:
                return dastore;
            case 0x53:
                return aastore;
            case 0x54:
                return bastore;
            case 0x55:
                return castore;
            case 0x56:
                return sastore;
            case 0x57:
                return pop;
            case 0x58:
                return pop2;
            case 0x59:
                return dup;
            case 0x5a:
                return dup_x1;
            case 0x5b:
                return dup_x2;
            case 0x5c:
                return dup2;
            case 0x5d:
                return dup2_x1;
            case 0x5e:
                return dup2_x2;
            case 0x5f:
                return swap;
            case 0x60:
                return iadd;
            case 0x61:
                return ladd;
            case 0x62:
                return fadd;
            case 0x63:
                return dadd;
            case 0x64:
                return isub;
            case 0x65:
                return lsub;
            case 0x66:
                return fsub;
            case 0x67:
                return dsub;
            case 0x68:
                return imul;
            case 0x69:
                return lmul;
            case 0x6a:
                return fmul;
            case 0x6b:
                return dmul;
            case 0x6c:
                return idiv;
            case 0x6d:
                return ldiv;
            case 0x6e:
                return fdiv;
            case 0x6f:
                return ddiv;
            case 0x70:
                return irem;
            case 0x71:
                return lrem;
            case 0x72:
                return frem;
            case 0x73:
                return drem;
            case 0x74:
                return ineg;
            case 0x75:
                return lneg;
            case 0x76:
                return fneg;
            case 0x77:
                return dneg;
            case 0x78:
                return ishl;
            case 0x79:
                return lshl;
            case 0x7a:
                return ishr;
            case 0x7b:
                return lshr;
            case 0x7c:
                return iushr;
            case 0x7d:
                return lushr;
            case 0x7e:
                return iand;
            case 0x7f:
                return land;
            case 0x80:
                return ior;
            case 0x81:
                return lor;
            case 0x82:
                return ixor;
            case 0x83:
                return lxor;
            case 0x84:
                return new IINC();
            case 0x85:
                return i2l;
            case 0x86:
                return i2f;
            case 0x87:
                return i2d;
            case 0x88:
                return l2i;
            case 0x89:
                return l2f;
            case 0x8a:
                return l2d;
            case 0x8b:
                return f2i;
            case 0x8c:
                return f2l;
            case 0x8d:
                return f2d;
            case 0x8e:
                return d2i;
            case 0x8f:
                return d2l;
            case 0x90:
                return d2f;
            case 0x91:
                return i2b;
            case 0x92:
                return i2c;
            case 0x93:
                return i2s;
            case 0x94:
                return lcmp;
            case 0x95:
                return fcmpl;
            case 0x96:
                return fcmpg;
            case 0x97:
                return dcmpl;
            case 0x98:
                return dcmpg;
            case 0x99:
                return new IFEQ();
            case 0x9a:
                return new IFNE();
            case 0x9b:
                return new IFLT();
            case 0x9c:
                return new IFGE();
            case 0x9d:
                return new IFGT();
            case 0x9e:
                return new IFLE();
            case 0x9f:
                return new IF_ICMPEQ();
            case 0xa0:
                return new IF_ICMPNE();
            case 0xa1:
                return new IF_ICMPLT();
            case 0xa2:
                return new IF_ICMPGE();
            case 0xa3:
                return new IF_ICMPGT();
            case 0xa4:
                return new IF_ICMPLE();
            case 0xa5:
                return new IF_ACMPEQ();
            case 0xa6:
                return new IF_ACMPNE();
            case 0xa7:
                return new GOTO();
            // case 0xa8:
            // 	return new JSR();
            // case 0xa9:
            // 	return new RET();
            case 0xaa:
                return new TABLE_SWITCH();
            case 0xab:
                return new LOOKUP_SWITCH();
            case 0xac:
                return ireturn;
            case 0xad:
                return lreturn;
            case 0xae:
                return freturn;
            case 0xaf:
                return dreturn;
            case 0xb0:
                return areturn;
            case 0xb1:
                return _return;
            case 0xb2:
                return new GET_STATIC();
            case 0xb3:
                return new PUT_STATIC();
            case 0xb4:
                return new GET_FIELD();
            case 0xb5:
                return new PUT_FIELD();
            case 0xb6:
                return new INVOKE_VIRTUAL();
            case 0xb7:
                return new INVOKE_SPECIAL();
            case 0xb8:
                return new INVOKE_STATIC();
            case 0xb9:
                return new INVOKE_INTERFACE();
            // case 0xba:
            // 	return new INVOKE_DYNAMIC();
            case 0xbb:
                return new NEW();
            case 0xbc:
                return new NEW_ARRAY();
            case 0xbd:
                return new ANEW_ARRAY();
            case 0xbe:
                return arraylength;
            case 0xbf:
                return athrow;
            case 0xc0:
                return new CHECK_CAST();
            case 0xc1:
                return new INSTANCE_OF();
            // case 0xc2:
            // 	return monitorenter;
            // case 0xc3:
            // 	return monitorexit;
            case 0xc4:
                return new WIDE();
            case 0xc5:
                return new MULTI_ANEW_ARRAY();
            case 0xc6:
                return new IFNULL();
            case 0xc7:
                return new IFNONNULL();
            case 0xc8:
                return new GOTO_W();
            // case 0xc9:
            // 	return new JSR_W();
            // case 0xca: breakpoint
            case 0xfe:
                return invoke_native;
            // case 0xff: impdep2
            default:
                throw new RuntimeException("Unsupported opcode: " + opCode);
        }
    }
}

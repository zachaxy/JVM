package instructions.extended;

import instructions.base.BytecodeReader;
import instructions.base.Instruction;
import instructions.loads.loaddouble.DLOAD;
import instructions.loads.loadfloat.FLOAD;
import instructions.loads.loadint.ILOAD;
import instructions.loads.loadlong.LLOAD;
import instructions.loads.loadref.ALOAD;
import instructions.math.iinc.IINC;
import instructions.stores.storedouble.DSTORE;
import instructions.stores.storefloat.FSTORE;
import instructions.stores.storeint.ISTORE;
import instructions.stores.storelong.LSTORE;
import instructions.stores.storeref.ASTORE;
import runtimedata.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/6 0006.
 * Desc:
 */
public class WIDE implements Instruction {

    Instruction modifiedInstruction;

    /*
    先从字节码中读取一字节的操作码，然后创建子指令实例，最后读取子指令的操作数。因为没有实现ret指令，所以暂时调用 RuntimeException 函数终止程序执行。
    */
    @Override
    public void fetchOperands(BytecodeReader reader) {
        int opCode = reader.readUint8();
        switch (opCode) {
            case 0x15:
                ILOAD iload = new ILOAD();
                iload.index = reader.readUint16();
                modifiedInstruction = iload;
                break;
            case 0x16:
                LLOAD lload = new LLOAD();
                lload.index = reader.readUint16();
                modifiedInstruction = lload;
                break;
            case 0x17:
                FLOAD fload = new FLOAD();
                fload.index = reader.readUint16();
                modifiedInstruction = fload;
                break;
            case 0x18:
                DLOAD dload = new DLOAD();
                dload.index = reader.readUint16();
                modifiedInstruction = dload;
                break;
            case 0x19:
                ALOAD aload = new ALOAD();
                aload.index = reader.readUint16();
                modifiedInstruction = aload;
                break;
            case 0x36:
                ISTORE istore = new ISTORE();
                istore.index = reader.readUint16();
                modifiedInstruction = istore;
                break;
            case 0x37:
                LSTORE lstore = new LSTORE();
                lstore.index = reader.readUint16();
                modifiedInstruction = lstore;
                break;
            case 0x38:
                FSTORE fstore = new FSTORE();
                fstore.index = reader.readUint16();
                modifiedInstruction = fstore;
                break;
            case 0x39:
                DSTORE dstore = new DSTORE();
                dstore.index = reader.readUint16();
                modifiedInstruction = dstore;
                break;
            case 0x3a:
                ASTORE astore = new ASTORE();
                astore.index = reader.readUint16();
                modifiedInstruction = astore;
                break;
            case 0x84:
                IINC iinc = new IINC();
                iinc.index = reader.readUint16();
                iinc.offset = reader.readInt16();
                modifiedInstruction = iinc;
                break;
            case 0xa9: // ret
                throw new RuntimeException("Unsupported opcode: 0xa9!");

        }
    }

    //wide指令只是增加了索引宽度，并不改变子指令操作，所以其Execute() 方法只要调用子指令的Execute()方法即可
    @Override
    public void execute(Zframe frame) {
        modifiedInstruction.execute(frame);
    }
}

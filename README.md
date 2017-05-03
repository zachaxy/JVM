# 解析class文件
## 魔数
> 例如PDF文件以4字节“%PDF”（0x25、0x50、0x44、0x46）开头，ZIP文件以2字节“PK”（0x50、0x4B）开头。class文件的魔数是“0xCAFEBABE”
> 开头的四字节,起标识作用

## 版本号
- 次版本号(m):2字节
- 主版本号(M):2字节

完整的版本号可以表示成“M.m”的形式。次版本号只在J2SE 1.2之前用过，从1.2开始基本上就没什么用了（都是0）。主版本号在J2SE 1.2之前是45，从1.2开始，每次有大的Java版本发布，都会加1。 

## 常量池
首字节:常量池的大小;接下来根据这个大小,生成常量信息数组;

1. 表头给出的常量池大小比实际大1,所以这样的话,虽然可能生成了这么大的,但是0不使用,直接从1开始;
2. 有效的常量池索引是1~n–1。0是无效索引，表示不指向任何常量
3. CONSTANT_Long_info和 CONSTANT_Double_info各占两个位置。也就是说，如果常量池中存在这两种常量，实际的常量数量比n–1还要少，而且1~n–1的某些数也会变成无效索引。

### 常量类型

- lang
- int
- double
- float
- char
- short
- byte
- boolean

### 类和方法

以常见的System.out.println("haha");举例
这用用到了out,其实out是System类中的一个成员变量,其类型为PrintStream
println是PrintStream中的一个方法;

那么在解析类文件的时候看到其解析到了一个FieldRef,class_index 和 name_and_type_index
class_index得到的是:java/lang/System,然后其有得到一个name_index : java/lang/System
name_and_type_index得到的是: NmeAndType ,其中包含一个 name_index 和 descriptor_index
分别对应:out 和 Ljava/io/PrintStream

### 常量池总结

常量池中的常量分为两类：字面量 和 符号引用

- 字面量
    - 数字常量
    - 字符串常量
- 符号引用
    - 类
    - 接口
    - 字段
    - 方法

字面量是可以直接获取到其值的,而符号引用是通过索引直接或者间接指向CONSTANT_Utf8_info常量,然后拿到其字面量的;

以CONSTANT_Fieldref_info为例(字段或者成员变量)

CONSTANT_Fieldref_info
- class_index
    - class_info(name_index)
        - Utf8_info

- name_and_type_index
    - NameAndTypeInfo(name_index + descriptor_index)
        - Utf8_info


## 属性表
方法的字节码存储在属性表中


​    

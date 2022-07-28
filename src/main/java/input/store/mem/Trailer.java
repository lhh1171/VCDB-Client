package input.store.mem;

//记载固定偏移量，region数量最大值是固定的, 所以该类大小可以确定，并一起装入VCFile文件落盘
public class Trailer {
//    该版本的表在File中的偏移
    long file_offset;
//    数据块索引的个数
    int block_count;

    public Trailer() {
        this.file_offset = 0;
        this.block_count = 0;
    }
}

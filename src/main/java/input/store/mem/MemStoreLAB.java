package input.store.mem;


/*
* 内存存储本地分配缓冲区。
MemStoreLAB 基本上是一个指针碰撞分配器，
* 它分配大 几个(MB) 字节 [] 块，
* 然后将其分配给请求切片到数组中的线程。
此类的目的是对抗 regionServer 中的堆碎片。
* 通过确保给定 memStore 中的所有 KeyValue 仅引用大块连续内存，
* 我们确保在刷新 memStore 时释放大块。
如果没有 MemStoreLAB，在插入过程中分配的字节数组最终会在整个堆中交错，
* 并且老一代会逐渐变得更加碎片化，直到发生 stop-the-world 压缩收集。
*/
public class MemStoreLAB {
}

package input.store.wal;

import input.forThread.HasThread;

//AsyncNotifier异步通知结果
//asyncWriter异步写入
//asyncSyncers同步机器
public class WalBuffer {
    private static class AsyncSyncer extends HasThread {
        @Override
        public void run() {

        }
    }
    private static class AsyncWriter extends HasThread {
        @Override
        public void run() {

        }
    }
    private static class AsyncNotifier extends HasThread {

        @Override
        public void run() {

        }
    }
}

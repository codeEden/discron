分布式定时任务平台，主要是根据spring quartz的集群方式，利用数据库的行锁来实现高可用、一致性。可以动态添加任务、任务分片等，致力于打造功能齐全、维护简单、依赖少的分布式定时任务平台。使用者能够快速搭建属于自己分布式任务平台，目前主要功能已经开发完成，任务分片还在开发中。主要实现技术包括：quartz、mysql、netty等

优势：
1.能够动态添加任务
2.机器down机可以将任务转移到其他可用机器
3.支持任务超时机制，设定超时时间，达到超时时间之后杀掉任务
4.支持自定义逻辑job逻辑和http通知两种方式

劣势
1.热点在mysql上，强依赖mysql
2.目前http执行逻辑方式只支持get方式（后续版本会增肌post）

beautiful-folds
===============

Combining streams and folds using [fs2](https://functional-streams-for-scala.github.io/fs2/) and [origami](https://github.com/atnos-org/origami).

### Benchmarks (Avg Time)

##### One Million elements

> jmh:run -i 10 -wi 10 -f 2 -t 1 com.github.gvolpe.folds.benchmarks.Fs2StreamFoldingBenchmark

```
[info] Benchmark                                           Mode  Cnt      Score      Error  Units
[info] Fs2StreamFoldingBenchmark.computeStats              avgt   20   5766.497 ± 1605.092  ms/op
[info] Fs2StreamFoldingBenchmark.computeStatsConcurrently  avgt   20   2192.093 ± 168.039   ms/op

```

##### Ten Million elements

> jmh:run -i 10 -wi 10 -f 2 -t 1 com.github.gvolpe.folds.benchmarks.Fs2StreamFoldingBenchmark

```
[info] Benchmark                                           Mode  Cnt      Score       Error  Units
[info] Fs2StreamFoldingBenchmark.computeStats              avgt   20  51794.843 ± 13006.907  ms/op
[info] Fs2StreamFoldingBenchmark.computeStatsConcurrently  avgt   20  26522.025 ±  1361.007  ms/op
```

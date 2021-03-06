/**
 * Copyright (c) 2017 SQUARESPACE, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.squarespace.template.plugins.platform.i18n;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.squarespace.template.CodeException;
import com.squarespace.template.Compiler;
import com.squarespace.template.Context;
import com.squarespace.template.FormatterTable;
import com.squarespace.template.PredicateTable;
import com.squarespace.template.plugins.CoreFormatters;
import com.squarespace.template.plugins.CorePredicates;


@Fork(1)
@Measurement(iterations = 5, time = 5)
@Warmup(iterations = 10, time = 2)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class DateBenchmark {

  @Benchmark
  public void genericDate(BenchmarkState state, Blackhole blackhole) throws CodeException {
    blackhole.consume(state.execute("{@|date %c}"));
  }

  @Benchmark
  public void internationalDateFull(BenchmarkState state, Blackhole blackhole) throws CodeException {
    blackhole.consume(state.execute("{@|datetime date-full time-full}"));
  }

  @State(Scope.Benchmark)
  public static class BenchmarkState {

    private Compiler compiler;

    private JsonNode timestamp = LongNode.valueOf(1);

    @Setup
    public void setupCompiler() throws RunnerException {
      this.compiler = new Compiler(formatterTable(), predicateTable());
    }

    public Context execute(String template) throws CodeException {
      return compiler.newExecutor()
          .template(template)
          .json(timestamp)
          .safeExecution(true)
          .execute();
    }
  }

  private static FormatterTable formatterTable() {
    FormatterTable table = new FormatterTable();
    table.register(new CoreFormatters());
    table.register(new InternationalFormatters());
    return table;
  }

  private static PredicateTable predicateTable() {
    PredicateTable table = new PredicateTable();
    table.register(new CorePredicates());
    return table;
  }


}

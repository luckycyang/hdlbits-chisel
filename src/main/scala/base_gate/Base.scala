package base_gate

import chisel3._
import circt.stage.{ChiselStage}


class wire() extends Module {
  val io = IO(new Bundle {
    val in = Input(Bool())
    val out = Output(Bool())
  }
  )
  io.out := io.in;
}

class GND() extends Module {
  val io = IO(new Bundle {
    val out = Output(Bool())
  })
  io.out := false.B
}

class NOR() extends Module {
  val io = IO(new Bundle {
    val in0, in1 = Input(Bool())
    val out = Output(Bool())
  })
  io.out := !(io.in0 | io.in1)
}

class AnotherGate() extends Module {
  val io = IO(new Bundle {
    val in0, in1 = Input(Bool())
    val out = Output(Bool())
  })
  io.out := io.in0 & !io.in1
}

class TwoGate() extends Module {
  val io = IO(new Bundle {
    val in0, in1, in2 = Input(Bool())
    val out = Output(Bool())
  })
  io.out := !(io.in0 ^ io.in1) ^ io.in2 // 没必要上Wire
}


class Te extends Module {
  val out0, out1 = IO(Output(Bool()))
  val in = IO(Input(Bool()))
  out0 := true.B & in
  out1 := false.B
}

object Base extends App {
  ChiselStage.emitSystemVerilogFile(new Te, Array("-td", "generated"), Array("--strip-debug-info"))
}


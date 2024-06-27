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


object Base extends App {
  ChiselStage.emitSystemVerilogFile(new wire, Array("-td", "generated"), Array("--strip-debug-info"))
}


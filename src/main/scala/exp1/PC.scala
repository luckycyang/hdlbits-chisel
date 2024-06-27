package exp1
import chisel3._
import circt.stage.ChiselStage
class PC extends Module {
  val in = IO(Input(UInt(8.W)))
  val out = IO(Output(UInt(8.W)))
  val reg = Reg(UInt(8.W))
  reg := reg + 1.U
  when (reset.asBool) {
    reg := 0.U
  }
  out := reg
}

object EXP extends App{
  ChiselStage.emitSystemVerilogFile(new PC, Array("-td", "generated"), Array("--strip-debug-info"))
}

import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
class EXPTester extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "PC Tester"
  // Wire
  it should "pass" in {
    test(new PC).withAnnotations(Seq(WriteVcdAnnotation)) {
      dut =>
        for (i <- 0 to 100){
          dut.in.poke(i.U)
          if (i % 8 == 0) {
            dut.reset.poke(true.B)
          } else {
            dut.reset.poke(false.B)
          }
          step(1)
        }
    }
  }
}
package ref

import chisel3._
import chisel3.util.{Cat, RegEnable}
import circt.stage.ChiselStage

class T_Bits() extends Module {
  val io = IO(new Bundle {
    val out = Output(UInt())
  })

}

class UTest(width: Int) extends Module {
  val io = IO(
    new Bundle {
      val in0, in1 = Input(UInt(width.W))
      val out = Output(UInt())
    }
  )
  io.out := io.in0 * io.in1
}

class RT(width: Int) extends Module {
  val out = IO(Output(UInt(width.W)))
  val in = IO(Input(Bool()))
  val reg = Reg(UInt(width.W))
  val regout = RegEnable(reg, 2.U, in)
  reg := reg + 1.U
  out := regout
}

object Ref extends App {
  ChiselStage.emitSystemVerilogFile(new RT(8), Array("-td", "generated"), Array("--strip-debug-info"))

}

import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class BaseTest extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "RegInit Tester"
  // Wire
  it should "pass" in {
    test(new RT(4)).withAnnotations(Seq(WriteVcdAnnotation)) {
      dut =>
        for (i <- 0 to 31) {
          if (i % 8 == 0 || i == 0) {
            dut.reset.poke(true.B)
          } else {
            dut.reset.poke(false.B)
          }
          if (i % 6 == 0) {
            dut.in.poke(true.B)
          } else {
            dut.in.poke(false.B)
          }
          step(1)
        }
    }
  }
}

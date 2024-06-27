package base_gate

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class BaseTest extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "Base Gate"
  it should "pass with out==in" in {
    test(new wire).withAnnotations(Seq(WriteVcdAnnotation)) {
      dut =>
        for (i <- 1 to 10) {
          if (i % 2 == 1) {
            dut.io.in.poke(true.B)
            dut.io.out.expect(true.B)
            dut.clock.step()
          } else {
            dut.io.in.poke(false.B)
            dut.io.out.expect(false.B)
            dut.clock.step()
          }
        }
    }
  }

}

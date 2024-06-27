package base_gate

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec


class BaseTest extends AnyFlatSpec with ChiselScalatestTester {
  val tester = new SimpleTester
  behavior of "Base Gate"
  // Wire
  it should "pass with out==in" in {
    test(new wire).withAnnotations(Seq(WriteVcdAnnotation)) {
      dut =>
        tester.wireTester(dut)
    }
  }
  // GND
  it should "pass with out == 0" in {
    test(new GND).withAnnotations(Seq(WriteVcdAnnotation)) {
      dut =>
        tester.gndTester(dut)
    }
  }
  // NOR
  it should "pass with out == in0 | in1" in {
    test(new NOR).withAnnotations(Seq(WriteVcdAnnotation)) {
      dut =>
        tester.norTester(dut)
    }
  }
  // AnotherGate
  it should "pass with out == in0 & !in1" in {
    test(new AnotherGate).withAnnotations(Seq(WriteVcdAnnotation)) {
      dut =>
        tester.anothergateTester(dut)
    }
  }
  // TwoGate
  it should "pass with out == !(in0 ^ in1) ^ in1" in {
    test(new TwoGate).withAnnotations(Seq(WriteVcdAnnotation)) {
      dut =>
        tester.twoTester(dut)
    }
  }

}

case class SimpleTester() {
  def wireTester(dut: wire) = {
    for (i <- 0 to 10) {
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

  def gndTester(dut: GND): Unit = {
    dut.io.out.expect(false.B)
    step(1)
  }

  def norTester(dut: NOR): Unit = {
    for (i <- Array(true, false)) {
      for (j <- Array(true, false)) {
        dut.io.in0.poke(i.B)
        dut.io.in1.poke(j.B)
        dut.io.out.expect((!(i | j)).B) // 不要 i.B | j.B 会错误
        step(1)
      }
    }
  }

  def anothergateTester(dut: AnotherGate): Unit = {
    for (i <- Array(true, false)) {
      for (j <- Array(true, false)) {
        dut.io.in0.poke(i.B)
        dut.io.in1.poke(j.B)
        dut.io.out.expect((i & !j).B)
        step(1)
      }
    }
  }

  def twoTester(dut: TwoGate): Unit = {
    for (i <- Array(true, false)) {
      for (j <- Array(true, false)) {
        for (k <- Array(true, false)) {
          dut.io.in0.poke(i.B)
          dut.io.in1.poke(j.B)
          dut.io.in2.poke(k.B)
          dut.io.out.expect((!(i ^ j) ^ k).B)
          step(1)
        }
      }
    }
  }
}
package gameboy.cpu_instructions;

import gameboy.CPU;
import gameboy.Flags;

import static gameboy.HelperFunctions.*;

// We suppress this warning because this class and its methods are dynamically called
// and therefore IntelliJ doesn't recognize their usage.
@SuppressWarnings("unused")
public class ExtendedInstructions implements CPUInstructions {
    @Opcode(value = 0x1000, length = 2, cycles = 1)
    public static void stop() {
    }

    @Opcode(value = 0x76, length = 1, cycles = 1)
    public static void halt() {
        CPU.HALTED = true;
        CPU.IME = true;
    }

    // region rlc
    @Opcode(value = 0xCB00, length = 2, cycles = 2)
    public static void rlc_b() {
        rlc_reg(CPU.BC.B);
    }

    @Opcode(value = 0xCB01, length = 2, cycles = 2)
    public static void rlc_c() {
        rlc_reg(CPU.BC.C);
    }

    @Opcode(value = 0xCB02, length = 2, cycles = 2)
    public static void rlc_d() {
        rlc_reg(CPU.DE.D);
    }

    @Opcode(value = 0xCB03, length = 2, cycles = 2)
    public static void rlc_e() {
        rlc_reg(CPU.DE.E);
    }

    @Opcode(value = 0xCB04, length = 2, cycles = 2)
    public static void rlc_h() {
        rlc_reg(CPU.HL.H);
    }

    @Opcode(value = 0xCB05, length = 2, cycles = 2)
    public static void rlc_l() {
        rlc_reg(CPU.HL.L);
    }

    @Opcode(value = 0xCB06, length = 2, cycles = 2)
    public static void rlc_hl() {
        CPU.memory.write(CPU.HL.getValue(), rlc_val(CPU.memory.read_byte(CPU.HL.getValue())));
    }

    @Opcode(value = 0xCB07, length = 2, cycles = 2)
    public static void rlc_a() {
        rlc_reg(CPU.AF.A);
    }
    // endregion

    // region rrc
    @Opcode(value = 0xCB08, length = 1, cycles = 1)
    public static void rrc_b() {
        rrc_reg(CPU.BC.B);
    }

    @Opcode(value = 0xCB09, length = 1, cycles = 1)
    public static void rrc_c() {
        rrc_reg(CPU.BC.C);
    }

    @Opcode(value = 0xCB0A, length = 1, cycles = 1)
    public static void rrc_d() {
        rrc_reg(CPU.DE.D);
    }

    @Opcode(value = 0xCB0B, length = 1, cycles = 1)
    public static void rrc_e() {
        rrc_reg(CPU.DE.E);
    }

    @Opcode(value = 0xCB0C, length = 1, cycles = 1)
    public static void rrc_h() {
        rrc_reg(CPU.HL.H);
    }

    @Opcode(value = 0xCB0D, length = 1, cycles = 1)
    public static void rrc_l() {
        rrc_reg(CPU.HL.L);
    }

    @Opcode(value = 0xCB0E, length = 1, cycles = 1)
    public static void rrc_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), rrc_val(CPU.memory.read_byte(CPU.HL.getValue())));
    }
    // endregion

    // region rl
    @Opcode(value = 0xCB10, length = 2, cycles = 2)
    public static void rl_b() {
        rl_reg(CPU.BC.B);
    }

    @Opcode(value = 0xCB11, length = 2, cycles = 2)
    public static void rl_c() {
        rl_reg(CPU.BC.C);
    }

    @Opcode(value = 0xCB12, length = 2, cycles = 2)
    public static void rl_d() {
        rl_reg(CPU.DE.D);
    }

    @Opcode(value = 0xCB13, length = 2, cycles = 2)
    public static void rl_e() {
        rl_reg(CPU.DE.E);
    }

    @Opcode(value = 0xCB14, length = 2, cycles = 2)
    public static void rl_h() {
        rl_reg(CPU.HL.H);
    }

    @Opcode(value = 0xCB15, length = 2, cycles = 2)
    public static void rl_l() {
        rl_reg(CPU.HL.L);
    }

    @Opcode(value = 0xCB16, length = 2, cycles = 2)
    public static void rl_hl() {
        CPU.memory.write(CPU.HL.getValue(), rl_val(CPU.memory.read_byte(CPU.HL.getValue())));
    }
    // endregion

    // region RR
    @Opcode(value = 0xCB18, length = 2, cycles = 2)
    public static void rr_b() {
        rr_reg(CPU.BC.B);
    }

    @Opcode(value = 0xCB19, length = 2, cycles = 2)
    public static void rr_c() {
        rr_reg(CPU.BC.C);
    }

    @Opcode(value = 0xCB1A, length = 2, cycles = 2)
    public static void rr_d() {
        rr_reg(CPU.DE.D);
    }

    @Opcode(value = 0xCB1B, length = 2, cycles = 2)
    public static void rr_e() {
        rr_reg(CPU.DE.E);
    }

    @Opcode(value = 0xCB1C, length = 2, cycles = 2)
    public static void rr_h() {
        rr_reg(CPU.HL.H);
    }

    @Opcode(value = 0xCB1D, length = 2, cycles = 2)
    public static void rr_l() {
        rr_reg(CPU.HL.L);
    }

    @Opcode(value = 0xCB1E, length = 2, cycles = 2)
    public static void rr_hl() {
        CPU.memory.write(CPU.HL.getValue(), (byte) rr_val((char) CPU.memory.read_byte(CPU.HL.getValue())));
    }

    @Opcode(value = 0xCB1F, length = 2, cycles = 2)
    public static void rr_a() {
        rr_reg(CPU.AF.A);
    }
    // endregion

    //region BIT OPS
    @Opcode(value = 0xCB40, length = 2, cycles = 2)
    public static void bit_0_b() {
        bit_reg(CPU.BC.B, 0);
    }

    @Opcode(value = 0xCB41, length = 2, cycles = 2)
    public static void bit_0_c() {
        bit_reg(CPU.BC.C, 0);
    }

    @Opcode(value = 0xCB42, length = 2, cycles = 2)
    public static void bit_0_d() {
        bit_reg(CPU.DE.D, 0);
    }

    @Opcode(value = 0xCB43, length = 2, cycles = 2)
    public static void bit_0_e() {
        bit_reg(CPU.DE.E, 0);
    }

    @Opcode(value = 0xCB44, length = 2, cycles = 2)
    public static void bit_0_h() {
        bit_reg(CPU.HL.H, 0);
    }

    @Opcode(value = 0xCB45, length = 2, cycles = 2)
    public static void bit_0_l() {
        bit_reg(CPU.HL.L, 0);
    }

    @Opcode(value = 0xCB46, length = 2, cycles = 2)
    public static void bit_0_from_hl() {
        bit_reg(CPU.memory.read_byte(CPU.HL.getValue()), 0);
    }

    @Opcode(value = 0xCB47, length = 2, cycles = 2)
    public static void bit_0_a() {
        bit_reg(CPU.AF.A, 0);
    }

    @Opcode(value = 0xCB48, length = 2, cycles = 2)
    public static void bit_1_b() {
        bit_reg(CPU.BC.B, 1);
    }

    @Opcode(value = 0xCB49, length = 2, cycles = 2)
    public static void bit_1_c() {
        bit_reg(CPU.BC.C, 1);
    }

    @Opcode(value = 0xCB4A, length = 2, cycles = 2)
    public static void bit_1_d() {
        bit_reg(CPU.DE.D, 1);
    }

    @Opcode(value = 0xCB4B, length = 2, cycles = 2)
    public static void bit_1_e() {
        bit_reg(CPU.DE.E, 1);
    }

    @Opcode(value = 0xCB4C, length = 2, cycles = 2)
    public static void bit_1_h() {
        bit_reg(CPU.HL.H, 1);
    }

    @Opcode(value = 0xCB4D, length = 2, cycles = 2)
    public static void bit_1_l() {
        bit_reg(CPU.HL.L, 1);
    }

    @Opcode(value = 0xCB4E, length = 2, cycles = 2)
    public static void bit_1_from_hl() {
        bit_reg(CPU.memory.read_byte(CPU.HL.getValue()), 1);
    }

    @Opcode(value = 0xCB4F, length = 2, cycles = 2)
    public static void bit_1_a() {
        bit_reg(CPU.AF.A, 1);
    }

    @Opcode(value = 0xCB50, length = 2, cycles = 2)
    public static void bit_2_b() {
        bit_reg(CPU.BC.B, 2);
    }

    @Opcode(value = 0xCB51, length = 2, cycles = 2)
    public static void bit_2_c() {
        bit_reg(CPU.BC.C, 2);
    }

    @Opcode(value = 0xCB52, length = 2, cycles = 2)
    public static void bit_2_d() {
        bit_reg(CPU.DE.D, 2);
    }

    @Opcode(value = 0xCB53, length = 2, cycles = 2)
    public static void bit_2_e() {
        bit_reg(CPU.DE.E, 2);
    }

    @Opcode(value = 0xCB54, length = 2, cycles = 2)
    public static void bit_2_h() {
        bit_reg(CPU.HL.H, 2);
    }

    @Opcode(value = 0xCB55, length = 2, cycles = 2)
    public static void bit_2_l() {
        bit_reg(CPU.HL.L, 2);
    }

    @Opcode(value = 0xCB56, length = 2, cycles = 2)
    public static void bit_2_from_hl() {
        bit_reg(CPU.memory.read_byte(CPU.HL.getValue()), 2);
    }

    @Opcode(value = 0xCB57, length = 2, cycles = 2)
    public static void bit_2_a() {
        bit_reg(CPU.AF.A, 2);
    }

    @Opcode(value = 0xCB58, length = 2, cycles = 2)
    public static void bit_3_b() {
        bit_reg(CPU.BC.B, 3);
    }

    @Opcode(value = 0xCB59, length = 2, cycles = 2)
    public static void bit_3_c() {
        bit_reg(CPU.BC.C, 3);
    }

    @Opcode(value = 0xCB5A, length = 2, cycles = 2)
    public static void bit_3_d() {
        bit_reg(CPU.DE.D, 3);
    }

    @Opcode(value = 0xCB5B, length = 2, cycles = 2)
    public static void bit_3_e() {
        bit_reg(CPU.DE.E, 3);
    }

    @Opcode(value = 0xCB5C, length = 2, cycles = 2)
    public static void bit_3_h() {
        bit_reg(CPU.HL.H, 3);
    }

    @Opcode(value = 0xCB5D, length = 2, cycles = 2)
    public static void bit_3_l() {
        bit_reg(CPU.HL.L, 3);
    }

    @Opcode(value = 0xCB5E, length = 2, cycles = 2)
    public static void bit_3_from_hl() {
        bit_reg(CPU.memory.read_byte(CPU.HL.getValue()), 3);
    }

    @Opcode(value = 0xCB5F, length = 2, cycles = 2)
    public static void bit_3_a() {
        bit_reg(CPU.AF.A, 3);
    }

    @Opcode(value = 0xCB60, length = 2, cycles = 2)
    public static void bit_4_b() {
        bit_reg(CPU.BC.B, 4);
    }

    @Opcode(value = 0xCB61, length = 2, cycles = 2)
    public static void bit_4_c() {
        bit_reg(CPU.BC.C, 4);
    }

    @Opcode(value = 0xCB62, length = 2, cycles = 2)
    public static void bit_4_d() {
        bit_reg(CPU.DE.D, 4);
    }

    @Opcode(value = 0xCB63, length = 2, cycles = 2)
    public static void bit_4_e() {
        bit_reg(CPU.DE.E, 4);
    }

    @Opcode(value = 0xCB64, length = 2, cycles = 2)
    public static void bit_4_h() {
        bit_reg(CPU.HL.H, 4);
    }

    @Opcode(value = 0xCB65, length = 2, cycles = 2)
    public static void bit_4_l() {
        bit_reg(CPU.HL.L, 4);
    }

    @Opcode(value = 0xCB66, length = 2, cycles = 2)
    public static void bit_4_from_hl() {
        bit_reg(CPU.memory.read_byte(CPU.HL.getValue()), 4);
    }

    @Opcode(value = 0xCB67, length = 2, cycles = 2)
    public static void bit_4_a() {
        bit_reg(CPU.AF.A, 4);
    }

    @Opcode(value = 0xCB68, length = 2, cycles = 2)
    public static void bit_5_b() {
        bit_reg(CPU.BC.B, 5);
    }

    @Opcode(value = 0xCB69, length = 2, cycles = 2)
    public static void bit_5_c() {
        bit_reg(CPU.BC.C, 5);
    }

    @Opcode(value = 0xCB6A, length = 2, cycles = 2)
    public static void bit_5_d() {
        bit_reg(CPU.DE.D, 5);
    }

    @Opcode(value = 0xCB6B, length = 2, cycles = 2)
    public static void bit_5_e() {
        bit_reg(CPU.DE.E, 5);
    }

    @Opcode(value = 0xCB6C, length = 2, cycles = 2)
    public static void bit_5_h() {
        bit_reg(CPU.HL.H, 5);
    }

    @Opcode(value = 0xCB6D, length = 2, cycles = 2)
    public static void bit_5_l() {
        bit_reg(CPU.HL.L, 5);
    }

    @Opcode(value = 0xCB6E, length = 2, cycles = 2)
    public static void bit_5_from_hl() {
        bit_reg(CPU.memory.read_byte(CPU.HL.getValue()), 5);
    }

    @Opcode(value = 0xCB6F, length = 2, cycles = 2)
    public static void bit_5_a() {
        bit_reg(CPU.AF.A, 5);
    }

    @Opcode(value = 0xCB70, length = 2, cycles = 2)
    public static void bit_6_b() {
        bit_reg(CPU.BC.B, 6);
    }

    @Opcode(value = 0xCB71, length = 2, cycles = 2)
    public static void bit_6_c() {
        bit_reg(CPU.BC.C, 6);
    }

    @Opcode(value = 0xCB72, length = 2, cycles = 2)
    public static void bit_6_d() {
        bit_reg(CPU.DE.D, 6);
    }

    @Opcode(value = 0xCB73, length = 2, cycles = 2)
    public static void bit_6_e() {
        bit_reg(CPU.DE.E, 6);
    }

    @Opcode(value = 0xCB74, length = 2, cycles = 2)
    public static void bit_6_h() {
        bit_reg(CPU.HL.H, 6);
    }

    @Opcode(value = 0xCB75, length = 2, cycles = 2)
    public static void bit_6_l() {
        bit_reg(CPU.HL.L, 6);
    }

    @Opcode(value = 0xCB76, length = 2, cycles = 2)
    public static void bit_6_from_hl() {
        bit_reg(CPU.memory.read_byte(CPU.HL.getValue()), 6);
    }

    @Opcode(value = 0xCB77, length = 2, cycles = 2)
    public static void bit_6_a() {
        bit_reg(CPU.AF.A, 6);
    }

    @Opcode(value = 0xCB78, length = 2, cycles = 2)
    public static void bit_7_b() {
        bit_reg(CPU.BC.B, 7);
    }

    @Opcode(value = 0xCB79, length = 2, cycles = 2)
    public static void bit_7_c() {
        bit_reg(CPU.BC.C, 7);
    }

    @Opcode(value = 0xCB7A, length = 2, cycles = 2)
    public static void bit_7_d() {
        bit_reg(CPU.DE.D, 7);
    }

    @Opcode(value = 0xCB7B, length = 2, cycles = 2)
    public static void bit_7_e() {
        bit_reg(CPU.DE.E, 7);
    }

    @Opcode(value = 0xCB7C, length = 2, cycles = 2)
    public static void bit_7_h() {
        bit_reg(CPU.HL.H, 7);
    }

    @Opcode(value = 0xCB7D, length = 2, cycles = 2)
    public static void bit_7_l() {
        bit_reg(CPU.HL.L, 7);
    }

    @Opcode(value = 0xCB7E, length = 2, cycles = 2)
    public static void bit_7_from_hl() {
        bit_reg(CPU.memory.read_byte(CPU.HL.getValue()), 7);
    }

    @Opcode(value = 0xCB7F, length = 2, cycles = 2)
    public static void bit_7_a() {
        bit_reg(CPU.AF.A, 7);
    }
    //endregion

    //region RES OPS
    @Opcode(value = 0xCB80, length = 2, cycles = 2)
    public static void res_0_b() {
        res_reg(CPU.BC.B, 0);
    }

    @Opcode(value = 0xCB81, length = 2, cycles = 2)
    public static void res_0_c() {
        res_reg(CPU.BC.C, 0);
    }

    @Opcode(value = 0xCB82, length = 2, cycles = 2)
    public static void res_0_d() {
        res_reg(CPU.DE.D, 0);
    }

    @Opcode(value = 0xCB83, length = 2, cycles = 2)
    public static void res_0_e() {
        res_reg(CPU.DE.E, 0);
    }

    @Opcode(value = 0xCB84, length = 2, cycles = 2)
    public static void res_0_h() {
        res_reg(CPU.HL.H, 0);
    }

    @Opcode(value = 0xCB85, length = 2, cycles = 2)
    public static void res_0_l() {
        res_reg(CPU.HL.L, 0);
    }

    @Opcode(value = 0xCB86, length = 2, cycles = 2)
    public static void res_0_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), res_reg(CPU.memory.read_byte(CPU.HL.getValue()), 0));
    }

    @Opcode(value = 0xCB87, length = 2, cycles = 2)
    public static void res_0_a() {
        res_reg(CPU.AF.A, 0);
    }

    @Opcode(value = 0xCB88, length = 2, cycles = 2)
    public static void res_1_b() {
        res_reg(CPU.BC.B, 1);
    }

    @Opcode(value = 0xCB89, length = 2, cycles = 2)
    public static void res_1_c() {
        res_reg(CPU.BC.C, 1);
    }

    @Opcode(value = 0xCB8A, length = 2, cycles = 2)
    public static void res_1_d() {
        res_reg(CPU.DE.D, 1);
    }

    @Opcode(value = 0xCB8B, length = 2, cycles = 2)
    public static void res_1_e() {
        res_reg(CPU.DE.E, 1);
    }

    @Opcode(value = 0xCB8C, length = 2, cycles = 2)
    public static void res_1_h() {
        res_reg(CPU.HL.H, 1);
    }

    @Opcode(value = 0xCB8D, length = 2, cycles = 2)
    public static void res_1_l() {
        res_reg(CPU.HL.L, 1);
    }

    @Opcode(value = 0xCB8E, length = 2, cycles = 2)
    public static void res_1_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), res_reg(CPU.memory.read_byte(CPU.HL.getValue()), 1));
    }

    @Opcode(value = 0xCB8F, length = 2, cycles = 2)
    public static void res_1_a() {
        res_reg(CPU.AF.A, 1);
    }

    @Opcode(value = 0xCB90, length = 2, cycles = 2)
    public static void res_2_b() {
        res_reg(CPU.BC.B, 2);
    }

    @Opcode(value = 0xCB91, length = 2, cycles = 2)
    public static void res_2_c() {
        res_reg(CPU.BC.C, 2);
    }

    @Opcode(value = 0xCB92, length = 2, cycles = 2)
    public static void res_2_d() {
        res_reg(CPU.DE.D, 2);
    }

    @Opcode(value = 0xCB93, length = 2, cycles = 2)
    public static void res_2_e() {
        res_reg(CPU.DE.E, 2);
    }

    @Opcode(value = 0xCB94, length = 2, cycles = 2)
    public static void res_2_h() {
        res_reg(CPU.HL.H, 2);
    }

    @Opcode(value = 0xCB95, length = 2, cycles = 2)
    public static void res_2_l() {
        res_reg(CPU.HL.L, 2);
    }

    @Opcode(value = 0xCB96, length = 2, cycles = 2)
    public static void res_2_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), res_reg(CPU.memory.read_byte(CPU.HL.getValue()), 2));
    }

    @Opcode(value = 0xCB97, length = 2, cycles = 2)
    public static void res_2_a() {
        res_reg(CPU.AF.A, 2);
    }

    @Opcode(value = 0xCB98, length = 2, cycles = 2)
    public static void res_3_b() {
        res_reg(CPU.BC.B, 3);
    }

    @Opcode(value = 0xCB99, length = 2, cycles = 2)
    public static void res_3_c() {
        res_reg(CPU.BC.C, 3);
    }

    @Opcode(value = 0xCB9A, length = 2, cycles = 2)
    public static void res_3_d() {
        res_reg(CPU.DE.D, 3);
    }

    @Opcode(value = 0xCB9B, length = 2, cycles = 2)
    public static void res_3_e() {
        res_reg(CPU.DE.E, 3);
    }

    @Opcode(value = 0xCB9C, length = 2, cycles = 2)
    public static void res_3_h() {
        res_reg(CPU.HL.H, 3);
    }

    @Opcode(value = 0xCB9D, length = 2, cycles = 2)
    public static void res_3_l() {
        res_reg(CPU.HL.L, 3);
    }

    @Opcode(value = 0xCB9E, length = 2, cycles = 2)
    public static void res_3_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), res_reg(CPU.memory.read_byte(CPU.HL.getValue()), 3));
    }

    @Opcode(value = 0xCB9F, length = 2, cycles = 2)
    public static void res_3_a() {
        res_reg(CPU.AF.A, 3);
    }

    @Opcode(value = 0xCBA0, length = 2, cycles = 2)
    public static void res_4_b() {
        res_reg(CPU.BC.B, 4);
    }

    @Opcode(value = 0xCBA1, length = 2, cycles = 2)
    public static void res_4_c() {
        res_reg(CPU.BC.C, 4);
    }

    @Opcode(value = 0xCBA2, length = 2, cycles = 2)
    public static void res_4_d() {
        res_reg(CPU.DE.D, 4);
    }

    @Opcode(value = 0xCBA3, length = 2, cycles = 2)
    public static void res_4_e() {
        res_reg(CPU.DE.E, 4);
    }

    @Opcode(value = 0xCBA4, length = 2, cycles = 2)
    public static void res_4_h() {
        res_reg(CPU.HL.H, 4);
    }

    @Opcode(value = 0xCBA5, length = 2, cycles = 2)
    public static void res_4_l() {
        res_reg(CPU.HL.L, 4);
    }

    @Opcode(value = 0xCBA6, length = 2, cycles = 2)
    public static void res_4_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), res_reg(CPU.memory.read_byte(CPU.HL.getValue()), 4));
    }

    @Opcode(value = 0xCBA7, length = 2, cycles = 2)
    public static void res_4_a() {
        res_reg(CPU.AF.A, 4);
    }

    @Opcode(value = 0xCBA8, length = 2, cycles = 2)
    public static void res_5_b() {
        res_reg(CPU.BC.B, 5);
    }

    @Opcode(value = 0xCBA9, length = 2, cycles = 2)
    public static void res_5_c() {
        res_reg(CPU.BC.C, 5);
    }

    @Opcode(value = 0xCBAA, length = 2, cycles = 2)
    public static void res_5_d() {
        res_reg(CPU.DE.D, 5);
    }

    @Opcode(value = 0xCBAB, length = 2, cycles = 2)
    public static void res_5_e() {
        res_reg(CPU.DE.E, 5);
    }

    @Opcode(value = 0xCBAC, length = 2, cycles = 2)
    public static void res_5_h() {
        res_reg(CPU.HL.H, 5);
    }

    @Opcode(value = 0xCBAD, length = 2, cycles = 2)
    public static void res_5_l() {
        res_reg(CPU.HL.L, 5);
    }

    @Opcode(value = 0xCBAE, length = 2, cycles = 2)
    public static void res_5_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), res_reg(CPU.memory.read_byte(CPU.HL.getValue()), 5));
    }

    @Opcode(value = 0xCBAF, length = 2, cycles = 2)
    public static void res_5_a() {
        res_reg(CPU.AF.A, 5);
    }

    @Opcode(value = 0xCBB0, length = 2, cycles = 2)
    public static void res_6_b() {
        res_reg(CPU.BC.B, 6);
    }

    @Opcode(value = 0xCBB1, length = 2, cycles = 2)
    public static void res_6_c() {
        res_reg(CPU.BC.C, 6);
    }

    @Opcode(value = 0xCBB2, length = 2, cycles = 2)
    public static void res_6_d() {
        res_reg(CPU.DE.D, 6);
    }

    @Opcode(value = 0xCBB3, length = 2, cycles = 2)
    public static void res_6_e() {
        res_reg(CPU.DE.E, 6);
    }

    @Opcode(value = 0xCBB4, length = 2, cycles = 2)
    public static void res_6_h() {
        res_reg(CPU.HL.H, 6);
    }

    @Opcode(value = 0xCBB5, length = 2, cycles = 2)
    public static void res_6_l() {
        res_reg(CPU.HL.L, 6);
    }

    @Opcode(value = 0xCBB6, length = 2, cycles = 2)
    public static void res_6_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), res_reg(CPU.memory.read_byte(CPU.HL.getValue()), 6));
    }

    @Opcode(value = 0xCBB7, length = 2, cycles = 2)
    public static void res_6_a() {
        res_reg(CPU.AF.A, 6);
    }

    @Opcode(value = 0xCBB8, length = 2, cycles = 2)
    public static void res_7_b() {
        res_reg(CPU.BC.B, 7);
    }

    @Opcode(value = 0xCBB9, length = 2, cycles = 2)
    public static void res_7_c() {
        res_reg(CPU.BC.C, 7);
    }

    @Opcode(value = 0xCBBA, length = 2, cycles = 2)
    public static void res_7_d() {
        res_reg(CPU.DE.D, 7);
    }

    @Opcode(value = 0xCBBB, length = 2, cycles = 2)
    public static void res_7_e() {
        res_reg(CPU.DE.E, 7);
    }

    @Opcode(value = 0xCBBC, length = 2, cycles = 2)
    public static void res_7_h() {
        res_reg(CPU.HL.H, 7);
    }

    @Opcode(value = 0xCBBD, length = 2, cycles = 2)
    public static void res_7_l() {
        res_reg(CPU.HL.L, 7);
    }

    @Opcode(value = 0xCBBE, length = 2, cycles = 2)
    public static void res_7_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), res_reg(CPU.memory.read_byte(CPU.HL.getValue()), 7));
    }

    @Opcode(value = 0xCBBF, length = 2, cycles = 2)
    public static void res_7_a() {
        res_reg(CPU.AF.A, 7);
    }
    //endregion

    //region SET OPS
    @Opcode(value = 0xCBC0, length = 2, cycles = 2)
    public static void set_0_b() {
        set_reg(CPU.BC.B, 0);
    }

    @Opcode(value = 0xCBC1, length = 2, cycles = 2)
    public static void set_0_c() {
        set_reg(CPU.BC.C, 0);
    }

    @Opcode(value = 0xCBC2, length = 2, cycles = 2)
    public static void set_0_d() {
        set_reg(CPU.DE.D, 0);
    }

    @Opcode(value = 0xCBC3, length = 2, cycles = 2)
    public static void set_0_e() {
        set_reg(CPU.DE.E, 0);
    }

    @Opcode(value = 0xCBC4, length = 2, cycles = 2)
    public static void set_0_h() {
        set_reg(CPU.HL.H, 0);
    }

    @Opcode(value = 0xCBC5, length = 2, cycles = 2)
    public static void set_0_l() {
        set_reg(CPU.HL.L, 0);
    }

    @Opcode(value = 0xCBC6, length = 2, cycles = 2)
    public static void set_0_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), set_reg(CPU.memory.read_byte(CPU.HL.getValue()), 0));
    }

    @Opcode(value = 0xCBC7, length = 2, cycles = 2)
    public static void set_0_a() {
        set_reg(CPU.AF.A, 0);
    }

    @Opcode(value = 0xCBC8, length = 2, cycles = 2)
    public static void set_1_b() {
        set_reg(CPU.BC.B, 1);
    }

    @Opcode(value = 0xCBC9, length = 2, cycles = 2)
    public static void set_1_c() {
        set_reg(CPU.BC.C, 1);
    }

    @Opcode(value = 0xCBCA, length = 2, cycles = 2)
    public static void set_1_d() {
        set_reg(CPU.DE.D, 1);
    }

    @Opcode(value = 0xCBCB, length = 2, cycles = 2)
    public static void set_1_e() {
        set_reg(CPU.DE.E, 1);
    }

    @Opcode(value = 0xCBCC, length = 2, cycles = 2)
    public static void set_1_h() {
        set_reg(CPU.HL.H, 1);
    }

    @Opcode(value = 0xCBCD, length = 2, cycles = 2)
    public static void set_1_l() {
        set_reg(CPU.HL.L, 1);
    }

    @Opcode(value = 0xCBCE, length = 2, cycles = 2)
    public static void set_1_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), set_reg(CPU.memory.read_byte(CPU.HL.getValue()), 1));
    }

    @Opcode(value = 0xCBCF, length = 2, cycles = 2)
    public static void set_1_a() {
        set_reg(CPU.AF.A, 1);
    }

    @Opcode(value = 0xCBD0, length = 2, cycles = 2)
    public static void set_2_b() {
        set_reg(CPU.BC.B, 2);
    }

    @Opcode(value = 0xCBD1, length = 2, cycles = 2)
    public static void set_2_c() {
        set_reg(CPU.BC.C, 2);
    }

    @Opcode(value = 0xCBD2, length = 2, cycles = 2)
    public static void set_2_d() {
        set_reg(CPU.DE.D, 2);
    }

    @Opcode(value = 0xCBD3, length = 2, cycles = 2)
    public static void set_2_e() {
        set_reg(CPU.DE.E, 2);
    }

    @Opcode(value = 0xCBD4, length = 2, cycles = 2)
    public static void set_2_h() {
        set_reg(CPU.HL.H, 2);
    }

    @Opcode(value = 0xCBD5, length = 2, cycles = 2)
    public static void set_2_l() {
        set_reg(CPU.HL.L, 2);
    }

    @Opcode(value = 0xCBD6, length = 2, cycles = 2)
    public static void set_2_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), set_reg(CPU.memory.read_byte(CPU.HL.getValue()), 2));
    }

    @Opcode(value = 0xCBD7, length = 2, cycles = 2)
    public static void set_2_a() {
        set_reg(CPU.AF.A, 2);
    }

    @Opcode(value = 0xCBD8, length = 2, cycles = 2)
    public static void set_3_b() {
        set_reg(CPU.BC.B, 3);
    }

    @Opcode(value = 0xCBD9, length = 2, cycles = 2)
    public static void set_3_c() {
        set_reg(CPU.BC.C, 3);
    }

    @Opcode(value = 0xCBDA, length = 2, cycles = 2)
    public static void set_3_d() {
        set_reg(CPU.DE.D, 3);
    }

    @Opcode(value = 0xCBDB, length = 2, cycles = 2)
    public static void set_3_e() {
        set_reg(CPU.DE.E, 3);
    }

    @Opcode(value = 0xCBDC, length = 2, cycles = 2)
    public static void set_3_h() {
        set_reg(CPU.HL.H, 3);
    }

    @Opcode(value = 0xCBDD, length = 2, cycles = 2)
    public static void set_3_l() {
        set_reg(CPU.HL.L, 3);
    }

    @Opcode(value = 0xCBDE, length = 2, cycles = 2)
    public static void set_3_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), set_reg(CPU.memory.read_byte(CPU.HL.getValue()), 3));
    }

    @Opcode(value = 0xCBDF, length = 2, cycles = 2)
    public static void set_3_a() {
        set_reg(CPU.AF.A, 3);
    }

    @Opcode(value = 0xCBE0, length = 2, cycles = 2)
    public static void set_4_b() {
        set_reg(CPU.BC.B, 4);
    }

    @Opcode(value = 0xCBE1, length = 2, cycles = 2)
    public static void set_4_c() {
        set_reg(CPU.BC.C, 4);
    }

    @Opcode(value = 0xCBE2, length = 2, cycles = 2)
    public static void set_4_d() {
        set_reg(CPU.DE.D, 4);
    }

    @Opcode(value = 0xCBE3, length = 2, cycles = 2)
    public static void set_4_e() {
        set_reg(CPU.DE.E, 4);
    }

    @Opcode(value = 0xCBE4, length = 2, cycles = 2)
    public static void set_4_h() {
        set_reg(CPU.HL.H, 4);
    }

    @Opcode(value = 0xCBE5, length = 2, cycles = 2)
    public static void set_4_l() {
        set_reg(CPU.HL.L, 4);
    }

    @Opcode(value = 0xCBE6, length = 2, cycles = 2)
    public static void set_4_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), set_reg(CPU.memory.read_byte(CPU.HL.getValue()), 4));
    }

    @Opcode(value = 0xCBE7, length = 2, cycles = 2)
    public static void set_4_a() {
        set_reg(CPU.AF.A, 4);
    }

    @Opcode(value = 0xCBE8, length = 2, cycles = 2)
    public static void set_5_b() {
        set_reg(CPU.BC.B, 5);
    }

    @Opcode(value = 0xCBE9, length = 2, cycles = 2)
    public static void set_5_c() {
        set_reg(CPU.BC.C, 5);
    }

    @Opcode(value = 0xCBEA, length = 2, cycles = 2)
    public static void set_5_d() {
        set_reg(CPU.DE.D, 5);
    }

    @Opcode(value = 0xCBEB, length = 2, cycles = 2)
    public static void set_5_e() {
        set_reg(CPU.DE.E, 5);
    }

    @Opcode(value = 0xCBEC, length = 2, cycles = 2)
    public static void set_5_h() {
        set_reg(CPU.HL.H, 5);
    }

    @Opcode(value = 0xCBED, length = 2, cycles = 2)
    public static void set_5_l() {
        set_reg(CPU.HL.L, 5);
    }

    @Opcode(value = 0xCBEE, length = 2, cycles = 2)
    public static void set_5_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), set_reg(CPU.memory.read_byte(CPU.HL.getValue()), 5));
    }

    @Opcode(value = 0xCBEF, length = 2, cycles = 2)
    public static void set_5_a() {
        set_reg(CPU.AF.A, 5);
    }

    @Opcode(value = 0xCBF0, length = 2, cycles = 2)
    public static void set_6_b() {
        set_reg(CPU.BC.B, 6);
    }

    @Opcode(value = 0xCBF1, length = 2, cycles = 2)
    public static void set_6_c() {
        set_reg(CPU.BC.C, 6);
    }

    @Opcode(value = 0xCBF2, length = 2, cycles = 2)
    public static void set_6_d() {
        set_reg(CPU.DE.D, 6);
    }

    @Opcode(value = 0xCBF3, length = 2, cycles = 2)
    public static void set_6_e() {
        set_reg(CPU.DE.E, 6);
    }

    @Opcode(value = 0xCBF4, length = 2, cycles = 2)
    public static void set_6_h() {
        set_reg(CPU.HL.H, 6);
    }

    @Opcode(value = 0xCBF5, length = 2, cycles = 2)
    public static void set_6_l() {
        set_reg(CPU.HL.L, 6);
    }

    @Opcode(value = 0xCBF6, length = 2, cycles = 2)
    public static void set_6_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), set_reg(CPU.memory.read_byte(CPU.HL.getValue()), 6));
    }

    @Opcode(value = 0xCBF7, length = 2, cycles = 2)
    public static void set_6_a() {
        set_reg(CPU.AF.A, 6);
    }

    @Opcode(value = 0xCBF8, length = 2, cycles = 2)
    public static void set_7_b() {
        set_reg(CPU.BC.B, 7);
    }

    @Opcode(value = 0xCBF9, length = 2, cycles = 2)
    public static void set_7_c() {
        set_reg(CPU.BC.C, 7);
    }

    @Opcode(value = 0xCBFA, length = 2, cycles = 2)
    public static void set_7_d() {
        set_reg(CPU.DE.D, 7);
    }

    @Opcode(value = 0xCBFB, length = 2, cycles = 2)
    public static void set_7_e() {
        set_reg(CPU.DE.E, 7);
    }

    @Opcode(value = 0xCBFC, length = 2, cycles = 2)
    public static void set_7_h() {
        set_reg(CPU.HL.H, 7);
    }

    @Opcode(value = 0xCBFD, length = 2, cycles = 2)
    public static void set_7_l() {
        set_reg(CPU.HL.L, 7);
    }

    @Opcode(value = 0xCBFE, length = 2, cycles = 2)
    public static void set_7_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), set_reg(CPU.memory.read_byte(CPU.HL.getValue()), 7));
    }

    @Opcode(value = 0xCBFF, length = 2, cycles = 2)
    public static void set_7_a() {
        set_reg(CPU.AF.A, 7);
    }
    //endregion


    // TODO: Move this
    @Opcode(value = 0x27, length = 1, cycles = 1)
    public static void daa() {
        if (CPU.AF.isSubtractionFlagOn()) {
            if (CPU.AF.isCarryFlagOn()) {
                CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() - 0x60));
            }
            if (CPU.AF.isHalfCarryFlagOn()) {
                CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() - 0x06));
            }
        } else {
            if (CPU.AF.isCarryFlagOn() || CPU.AF.A.getValue() > 0x99) {
                CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() + 0x60));
                CPU.turnOnFlags(Flags.CARRY);
            }
            if (CPU.AF.isHalfCarryFlagOn() || (CPU.AF.A.getValue() & 0x0F) > 0x09) {
                CPU.AF.A.setValue((byte) (CPU.AF.A.getValue() + 0x06));
            }
        }
        if (CPU.AF.A.getValue() == 0) {
            CPU.turnOnFlags(Flags.ZERO);
        } else {
            CPU.turnOffFlags(Flags.ZERO);
        }
        CPU.turnOffFlags(Flags.HALF_CARRY);
    }

    // region SLA
    @Opcode(value = 0xCB20, length = 2, cycles = 2)
    public static void sla_b() {
        sla_reg(CPU.BC.B);
    }

    @Opcode(value = 0xCB21, length = 2, cycles = 2)
    public static void sla_c() {
        sla_reg(CPU.BC.C);
    }

    @Opcode(value = 0xCB22, length = 2, cycles = 2)
    public static void sla_d() {
        sla_reg(CPU.DE.D);
    }

    @Opcode(value = 0xCB23, length = 2, cycles = 2)
    public static void sla_e() {
        sla_reg(CPU.DE.E);
    }

    @Opcode(value = 0xCB24, length = 2, cycles = 2)
    public static void sla_h() {
        sla_reg(CPU.HL.H);
    }

    @Opcode(value = 0xCB25, length = 2, cycles = 2)
    public static void sla_l() {
        sla_reg(CPU.HL.L);
    }

    @Opcode(value = 0xCB26, length = 2, cycles = 2)
    public static void sla_hl() {
        CPU.memory.write(CPU.HL.getValue(), (byte) sla_val((char) CPU.memory.read_byte(CPU.HL.getValue())));
    }

    @Opcode(value = 0xCB27, length = 2, cycles = 2)
    public static void sla_a() {
        sla_reg(CPU.AF.A);
    }
    // endregion

    @Opcode(value = 0xCB28, length = 2, cycles = 2)
    public static void sra_b() {
        sra_reg(CPU.BC.B);
    }

    @Opcode(value = 0xCB29, length = 2, cycles = 2)
    public static void sra_c() {
        sra_reg(CPU.BC.C);
    }

    @Opcode(value = 0xCB2A, length = 2, cycles = 2)
    public static void sra_d() {
        sra_reg(CPU.DE.D);
    }

    @Opcode(value = 0xCB2B, length = 2, cycles = 2)
    public static void sra_e() {
        sra_reg(CPU.DE.E);
    }

    @Opcode(value = 0xCB2C, length = 2, cycles = 2)
    public static void sra_h() {
        sra_reg(CPU.HL.H);
    }

    @Opcode(value = 0xCB2D, length = 2, cycles = 2)
    public static void sra_l() {
        sra_reg(CPU.HL.L);
    }

    @Opcode(value = 0xCB2E, length = 2, cycles = 2)
    public static void sra_from_hl() {
        CPU.memory.write(CPU.HL.getValue(), (byte) sra_val((char) CPU.memory.read_byte(CPU.HL.getValue())));
    }

    @Opcode(value = 0xCB2F, length = 2, cycles = 2)
    public static void sra_a() {
        sra_reg(CPU.AF.A);
    }


}
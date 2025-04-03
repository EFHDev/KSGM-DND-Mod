package Networking;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function8;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public class CodecUtil {

    public static <B, C, T1, T2, T3> StreamCodec<B, C> composite(
            final StreamCodec<? super B, T1> T1T, final Function<C, T1> T1F,
            final StreamCodec<? super B, T2> T2T, final Function<C, T2> T2F,
            final StreamCodec<? super B, T3> T3T, final Function<C, T3> T3F,

            final Function3<T1, T2, T3,  C> p_331335_) {
        return new StreamCodec<B, C>() {
            public C decode(B p_330310_) {
                T1 t1 = T1T.decode(p_330310_);
                T2 t2 = T2T.decode(p_330310_);
                T3 t3 = T3T.decode(p_330310_);



                return p_331335_.apply(t1, t2, t3);
            }

            public void encode(B p_332052_, C p_331912_) {
                T1T.encode(p_332052_, T1F.apply(p_331912_));
                T2T.encode(p_332052_, T2F.apply(p_331912_));
                T3T.encode(p_332052_, T3F.apply(p_331912_));



            }
        };
    }
}


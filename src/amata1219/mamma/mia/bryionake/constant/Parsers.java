package amata1219.mamma.mia.bryionake.constant;

import amata1219.mamma.mia.bryionake.adt.Either;
import amata1219.mamma.mia.bryionake.dsl.parser.FailableParser;
import amata1219.mamma.mia.bryionake.interval.Interval;
import amata1219.mamma.mia.bryionake.interval.Endpoint;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.function.Function;
import java.util.function.Supplier;

import static amata1219.mamma.mia.bryionake.adt.Either.failure;
import static amata1219.mamma.mia.bryionake.adt.Either.success;
import static amata1219.mamma.mia.bryionake.interval.Endpoint.closedEndpoint;

public class Parsers {

    public static final FailableParser<String> str = Either::success;

    public static final FailableParser<Boolean> bool = define(Boolean::parseBoolean, () -> "true または false を指定して下さい。");

    public static final FailableParser<Byte> i8 = define(Byte::parseByte, () -> "8bit整数値を指定して下さい。");

    public static final FailableParser<Short> i16 = define(Short::parseShort, () -> "16bit整数値を指定して下さい。");

    public static final FailableParser<Integer> i32 = define(Integer::parseInt, () -> "32bit整数値を指定して下さい。");

    public static final FailableParser<Long> i64 = define(Long::parseLong, () -> "64bit整数値を指定して下さい。");

    public static final FailableParser<Float> f32 = define(Float::parseFloat, () -> "32bit小数値を指定して下さい。");

    public static final FailableParser<Double> f64 = define(Double::parseDouble, () -> "64bit小数値を指定して下さい。");

    public static final FailableParser<Integer> u32 = define(i32, new Interval<>(Endpoint.closedEndpoint(0), Endpoint.closedEndpoint(Integer.MAX_VALUE)), () -> "32bit非負整数値を指定して下さい。");

    public static final FailableParser<Long> u64 = define(i64, new Interval<>(Endpoint.closedEndpoint(0L), Endpoint.closedEndpoint(Long.MAX_VALUE)), () -> "64bit非負整数値を指定して下さい。");

    public static final FailableParser<OfflinePlayer> player = define(arg -> {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(arg);
        if (!offlinePlayer.hasPlayedBefore()) throw new IllegalArgumentException();
        return offlinePlayer;
    }, () -> "指定されたプレイヤーはこのサーバーに一度もログインしたことがありません。");

    public static final FailableParser<Player> onlinePlayer = player.append(player -> player.isOnline() ? success(player.getPlayer()) : failure("指定されたプレイヤーはオンラインではありません。"));

    public static final FailableParser<World> world = define(defineFailableMapper(Bukkit::getWorld), () -> "指定されたワールドは存在しません。");

    public static <T> FailableParser<T> define(Function<String, T> mapper, Supplier<String> errorMessage) {
        return arg -> {
            try {
                return success(mapper.apply(arg));
            } catch (Exception e) {
                return failure(Constants.ERROR_MESSAGE_PREFIX + "引数のパース処理に失敗しました。" + errorMessage.get());
            }
        };
    }

    public static <N extends Number & Comparable<N>> FailableParser<N> define(FailableParser<N> baseParser, Interval<N> restriction, Supplier<String> errorMessage) {
        return baseParser.append(n -> restriction.contains(n) ? success(n) : failure(Constants.ERROR_MESSAGE_PREFIX + "引数のパース処理に失敗しました。" + errorMessage.get()));
    }

    public static <T> Function<String, T> defineFailableMapper(Function<String, T> pureGetter) {
        return pureGetter.andThen(v -> {
            if (v == null) throw new IllegalArgumentException();
            return v;
        });
    }

}

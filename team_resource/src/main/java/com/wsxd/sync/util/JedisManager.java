package com.wsxd.sync.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Repository
public class JedisManager {

	@Autowired
	private JedisPool jedisPool;

	public JedisPool getJedisPool() {
		return this.jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public Jedis getResource() {
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
		} catch (Exception e) {
			throw new JedisConnectionException(e);
		}
		return jedis;
	}

	/* --- Hash（哈希表） -------------------------------------------------- */

	/**
	 * 
	 * <b>HMSET key field value [field value ...]</b> <br>
	 * <br>
	 * 
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中。 <br>
	 * 此命令会覆盖哈希表中已存在的域。 <br>
	 * 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作。 <br>
	 * 
	 * @param key
	 * @param hash
	 * @return 如果命令执行成功，返回 OK 。 <br>
	 *         当 key 不是哈希表(hash)类型时，返回一个错误。 <br>
	 * 
	 */
	public String hmset(String key, Map<String, String> hash) {
		Jedis jedis = getResource();
		String result = jedis.hmset(key, hash);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>HMGET key field [field ...]</b> <br>
	 * <br>
	 * 
	 * 返回哈希表 key 中，一个或多个给定域的值。 <br>
	 * 如果给定的域不存在于哈希表，那么返回一个 nil 值。 <br>
	 * 因为不存在的 key 被当作一个空哈希表来处理，所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表。 <br>
	 * 
	 * @param key
	 * @param fields
	 * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
	 * 
	 */
	public List<String> hmget(String key, String... fields) {
		Jedis jedis = getResource();
		List<String> result = jedis.hmget(key, fields);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>HSET key field value</b> <br>
	 * <br>
	 * 
	 * 将哈希表 key 中的域 field 的值设为 value 。 <br>
	 * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。 <br>
	 * 如果域 field 已经存在于哈希表中，旧值将被覆盖。 <br>
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。 <br>
	 *         如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
	 * 
	 */
	public Long hset(String key, String field, String value) {
		Jedis jedis = getResource();
		Long result = jedis.hset(key, field, value);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>HGET key field</b> <br>
	 * <br>
	 * 
	 * 返回哈希表 key 中给定域 field 的值。 <br>
	 * 
	 * @param key
	 * @param field
	 * @return 给定域的值。 <br>
	 *         当给定域不存在或是给定 key 不存在时，返回 nil 。
	 * 
	 */
	public String hget(String key, String field) {
		Jedis jedis = getResource();
		String result = jedis.hget(key, field);
		jedis.close();
		return result;
	}

	/* --- Set（集合） -------------------------------------------------- */

	/**
	 * 
	 * <b>SADD key member [member ...]</b> <br>
	 * <br>
	 * 
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。 <br>
	 * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。 <br>
	 * 当 key 不是集合类型时，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param members
	 * @return 被添加到集合中的新元素的数量，不包括被忽略的元素。
	 * 
	 */
	public Long sadd(String key, String... members) {
		Jedis jedis = getResource();
		Long result = jedis.sadd(key, members);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>SISMEMBER key member</b> <br>
	 * <br>
	 * 
	 * 判断 member 元素是否集合 key 的成员。 <br>
	 * 
	 * @param key
	 * @param member
	 * @return 如果 member 元素是集合的成员，返回 true 。 <br>
	 *         如果 member 元素不是集合的成员，或 key 不存在，返回 false 。
	 * 
	 */
	public Boolean sismember(String key, String member) {
		Jedis jedis = getResource();
		Boolean result = jedis.sismember(key, member);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>SMEMBERS key</b> <br>
	 * <br>
	 * 
	 * 返回集合 key 中的所有成员。<br>
	 * 
	 * 不存在的 key 被视为空集合。
	 * 
	 * @param key
	 * @return 集合中的所有成员。
	 * 
	 */
	public Set<String> smembers(String key) {
		Jedis jedis = getResource();
		Set<String> result = jedis.smembers(key);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>SREM key member [member ...]</b> <br>
	 * <br>
	 * 
	 * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。 <br>
	 * 当 key 不是集合类型，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param members
	 * @return 被成功移除的元素的数量，不包括被忽略的元素。
	 * 
	 */
	public Long srem(String key, String... members) {
		Jedis jedis = getResource();
		Long result = jedis.srem(key, members);
		jedis.close();
		return result;
	}

	/* --- String（字符串） -------------------------------------------------- */

	/**
	 * 
	 * <b>MSET key value [key value ...]</b> <br>
	 * <br>
	 * 
	 * 同时设置一个或多个 key-value 对。 <br>
	 * 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，如果这不是你所希望的效果，请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。 <br>
	 * MSET 是一个原子性(atomic)操作，所有给定 key 都会在同一时间内被设置，某些给定 key 被更新而另一些给定 key 没有改变的情况，不可能发生。 <br>
	 * 
	 * @param keysvalues
	 * @return 总是返回 OK (因为 MSET 不可能失败)
	 * 
	 */
	public String mset(String... keysvalues) {
		Jedis jedis = getResource();
		String result = jedis.mset(keysvalues);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>MGET key [key ...]</b> <br>
	 * <br>
	 * 
	 * 返回所有(一个或多个)给定 key 的值。 <br>
	 * 如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil 。因此，该命令永不失败。 <br>
	 * 
	 * @param keys
	 * @return 一个包含所有给定 key 的值的列表。
	 * 
	 */
	public List<String> mget(String... keys) {
		Jedis jedis = getResource();
		List<String> result = jedis.mget(keys);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>GET key</b> <br>
	 * <br>
	 * 
	 * 返回 key 所关联的字符串值。 <br>
	 * 如果 key 不存在那么返回特殊值 nil 。 <br>
	 * 假如 key 储存的值不是字符串类型，返回一个错误，因为 GET 只能用于处理字符串值。 <br>
	 * 
	 * @param key
	 * @return 当 key 不存在时，返回 nil ，否则，返回 key 的值。 <br>
	 *         如果 key 不是字符串类型，那么返回一个错误。
	 * 
	 */
	public String get(String key) {
		Jedis jedis = getResource();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>SETEX key seconds value</b> <br>
	 * <br>
	 * 
	 * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。 <br>
	 * 如果 key 已经存在， SETEX 命令将覆写旧值。 <br>
	 * <br>
	 * 
	 * 这个命令类似于以下两个命令： <br>
	 * SET key value <br>
	 * EXPIRE key seconds # 设置生存时间 <br>
	 * <br>
	 * 
	 * 不同之处是， SETEX 是一个原子性(atomic)操作，关联值和设置生存时间两个动作会在同一时间内完成，该命令在 Redis 用作缓存时，非常实用。 <br>
	 * 
	 * @param key
	 * @param seconds
	 * @param value
	 * @return 设置成功时返回 OK 。 <br>
	 *         当 seconds 参数不合法时，返回一个错误。
	 * 
	 */
	public String setex(String key, int seconds, String value) {
		Jedis jedis = getResource();
		String result = jedis.setex(key, seconds, value);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>INCR key</b> <br>
	 * <br>
	 * 
	 * 将 key 中储存的数字值增一。 <br>
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。 <br>
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。 <br>
	 * 本操作的值限制在 64 位(bit)有符号数字表示之内。 <br>
	 * 
	 * @param key
	 * @return 执行 INCR 命令之后 key 的值。
	 * 
	 */
	public Long incr(String key) {
		Jedis jedis = getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	/* --- Key（键） -------------------------------------------------- */

	/**
	 * 
	 * <b>DEL key [key ...]</b> <br>
	 * <br>
	 * 
	 * 删除给定的一个或多个 key 。 <br>
	 * 不存在的 key 会被忽略。 <br>
	 * 
	 * @param keys
	 * @return 被删除 key 的数量。
	 * 
	 */
	public Long del(String... keys) {
		Jedis jedis = getResource();
		Long result = jedis.del(keys);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>EXISTS key</b> <br>
	 * <br>
	 * 
	 * 检查给定 key 是否存在。 <br>
	 * 
	 * @param key
	 * @return 若 key 存在，返回 true ，否则返回 false 。
	 * 
	 */
	public Boolean exists(String key) {
		Jedis jedis = getResource();
		Boolean result = jedis.exists(key);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>EXPIRE key seconds</b> <br>
	 * <br>
	 * 
	 * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。 <br>
	 * 在 Redis 中，带有生存时间的 key 被称为『易失的』(volatile)。 <br>
	 * 生存时间可以通过使用 DEL 命令来删除整个 key 来移除，或者被 SET 和 GETSET 命令覆写(overwrite)，这意味着，如果一个命令只是修改(alter)一个带生存时间的 key 的值而不是用一个新的 key 值来代替(replace)它的话，那么生存时间不会被改变。 <br>
	 * 比如说，对一个 key 执行 INCR 命令，对一个列表进行 LPUSH 命令，或者对一个哈希表执行 HSET 命令，这类操作都不会修改 key 本身的生存时间。 <br>
	 * 另一方面，如果使用 RENAME 对一个 key 进行改名，那么改名后的 key 的生存时间和改名前一样。 <br>
	 * RENAME 命令的另一种可能是，尝试将一个带生存时间的 key 改名成另一个带生存时间的 another_key ，这时旧的 another_key (以及它的生存时间)会被删除，然后旧的 key 会改名为 another_key ，因此，新的 another_key 的生存时间也和原本的 key 一样。 <br>
	 * 使用 PERSIST 命令可以在不删除 key 的情况下，移除 key 的生存时间，让 key 重新成为一个『持久的』(persistent) key 。 <br>
	 * <br>
	 * 
	 * <b>更新生存时间</b> <br>
	 * <br>
	 * 
	 * 可以对一个已经带有生存时间的 key 执行 EXPIRE 命令，新指定的生存时间会取代旧的生存时间。 <br>
	 * <br>
	 * 
	 * <b>过期时间的精确度</b> <br>
	 * <br>
	 * 
	 * 在 Redis 2.4 版本中，过期时间的延迟在 1 秒钟之内 —— 也即是，就算 key 已经过期，但它还是可能在过期之后一秒钟之内被访问到，而在新的 Redis 2.6 版本中，延迟被降低到 1 毫秒之内。 <br>
	 * 
	 * @param key
	 * @param seconds
	 * @return 设置成功返回 1 。 <br>
	 *         当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的生存时间)，返回 0 。
	 * 
	 */
	public Long expire(String key, int seconds) {
		Jedis jedis = getResource();
		Long result = jedis.expire(key, seconds);
		jedis.close();
		return result;
	}

	/* --- List（列表） -------------------------------------------------- */

	/**
	 * 
	 * <b>LPUSH key value [value ...]</b> <br>
	 * <br>
	 * 
	 * 将一个或多个值 value 插入到列表 key 的表头 <br>
	 * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。 <br>
	 * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。 <br>
	 * 当 key 存在但不是列表类型时，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param strings
	 * @return 执行 LPUSH 命令后，列表的长度。
	 * 
	 */
	public Long lpush(String key, String... strings) {
		Jedis jedis = getResource();
		Long result = jedis.lpush(key, strings);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>RPUSH key value [value ...]</b> <br>
	 * <br>
	 * 
	 * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。 <br>
	 * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾：比如对一个空列表 mylist 执行 RPUSH mylist a b c ，得出的结果列表为 a b c ，等同于执行命令 RPUSH mylist a 、 RPUSH mylist b 、 RPUSH mylist c 。 <br>
	 * 如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。 <br>
	 * 当 key 存在但不是列表类型时，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param strings
	 * @return 执行 RPUSH 操作后，表的长度。
	 * 
	 */
	public Long rpush(String key, String... strings) {
		Jedis jedis = getResource();
		Long result = jedis.rpush(key, strings);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>LPOP key</b> <br>
	 * <br>
	 * 
	 * 移除并返回列表 key 的头元素。 <br>
	 * 
	 * @param key
	 * @return 列表的头元素。 <br>
	 *         当 key 不存在时，返回 nil 。
	 * 
	 */
	public String lpop(String key) {
		Jedis jedis = getResource();
		String result = jedis.lpop(key);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>RPOP key</b> <br>
	 * <br>
	 * 
	 * 移除并返回列表 key 的尾元素。 <br>
	 * 
	 * @param key
	 * @return 列表的尾元素。 <br>
	 *         当 key 不存在时，返回 nil 。
	 * 
	 */
	public String rpop(String key) {
		Jedis jedis = getResource();
		String result = jedis.rpop(key);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>LRANGE key start stop</b> <br>
	 * <br>
	 * 
	 * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。 <br>
	 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 <br>
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。 <br>
	 * <br>
	 * 
	 * <b>注意LRANGE命令和编程语言区间函数的区别</b> <br>
	 * <br>
	 * 
	 * 假如你有一个包含一百个元素的列表，对该列表执行 LRANGE list 0 10 ，结果是一个包含11个元素的列表，这表明 stop 下标也在 LRANGE 命令的取值范围之内(闭区间)，这和某些语言的区间函数可能不一致，比如Ruby的 Range.new 、 Array#slice 和Python的 range() 函数。 <br>
	 * <br>
	 * 
	 * <b>超出范围的下标</b> <br>
	 * <br>
	 * 
	 * 超出范围的下标值不会引起错误。 <br>
	 * 如果 start 下标比列表的最大下标 end ( LLEN list 减去 1 )还要大，那么 LRANGE 返回一个空列表。 <br>
	 * 如果 stop 下标比 end 下标还要大，Redis将 stop 的值设置为 end 。 <br>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return 一个列表，包含指定区间内的元素。
	 * 
	 */
	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = getResource();
		List<String> result = jedis.lrange(key, start, end);
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>LREM key count value</b> <br>
	 * <br>
	 * 
	 * 根据参数 count 的值，移除列表中与参数 value 相等的元素。 <br>
	 * <br>
	 * 
	 * count 的值可以是以下几种： <br>
	 * 
	 * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。 <br>
	 * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。 <br>
	 * count = 0 : 移除表中所有与 value 相等的值。 <br>
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return 被移除元素的数量。
	 * 
	 */
	public Long lrem(String key, long count, String value) {
		Jedis jedis = getResource();
		Long result = jedis.lrem(key, count, value);
		jedis.close();
		return result;

	}

	/* --- Server（服务器） -------------------------------------------------- */

	/**
	 * 
	 * <b>DBSIZE</b> <br>
	 * <br>
	 * 
	 * 返回当前数据库的 key 的数量。 <br>
	 * 
	 * @return 当前数据库的 key 的数量。
	 * 
	 */
	public Long dbSize() {
		Jedis jedis = getResource();
		Long result = jedis.dbSize();
		jedis.close();
		return result;
	}

	/**
	 * 
	 * <b>INFO [section]</b> <br>
	 * <br>
	 * 
	 * 以一种易于解释（parse）且易于阅读的格式，返回关于 Redis 服务器的各种信息和统计数值。
	 * 
	 * @param section
	 * @return
	 * 
	 */
	public String info(String section) {
		Jedis jedis = getResource();
		String result = jedis.info(section);
		jedis.close();
		return result;
	}
}
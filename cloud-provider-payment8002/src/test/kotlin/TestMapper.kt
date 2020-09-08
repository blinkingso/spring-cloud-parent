import com.yz.payment.PaymentProvider8002
import com.yz.payment.mapper.PaymentMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [PaymentProvider8002::class])
class TestMapper {

    @Autowired
    private lateinit var paymentMapper: PaymentMapper

    @Test
    fun testMapper() {
        paymentMapper.findAll().forEach {
            println(it.serial)
        }
    }
}
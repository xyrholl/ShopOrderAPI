package kr.co._29cm.homework.domian;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PricePolicy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pricePolicyId")
    private long id;
    private int shippingFee;
    private int FreeBaseAmount;
    @CreationTimestamp
    private LocalDateTime createTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "pricePolicy", fetch = FetchType.LAZY)
    private List<Order> Orders = new ArrayList<>();

}

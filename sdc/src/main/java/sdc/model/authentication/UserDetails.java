package sdc.model.authentication;

import com.leadingsoft.bizfuse.common.jpa.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import sdc.enums.Gender;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class UserDetails extends AbstractModel {
    private static final long serialVersionUID = -1659092271598620571L;

    //////////////////////////////////////////////////
    /// 用户的基本信息
    //////////////////////////////////////////////////
    /**
     * 姓名
     */
    @Length(max = 50)
    @Column(length = 50)
    private String name;

    /**
     * 昵称
     */
    @Length(max = 50)
    @Column(length = 50)
    private String nickname;

    /**
     * 生日
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;

    /**
     * 性别
     */
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * 地区（国家）
     */
    @Length(max = 100)
    @Column(length = 100)
    private String country;

    /**
     * 地区（省）
     */
    @Length(max = 100)
    @Column(length = 100)
    private String province;

    /**
     * 地区（市）
     */
    @Length(max = 100)
    @Column(length = 100)
    private String city;

    /**
     * 地区（区县）
     */
    @Length(max = 200)
    @Column(length = 200)
    private String district;

    /**
     * 详细地址
     */
    @Length(max = 200)
    @Column(length = 200)
    private String address;
}

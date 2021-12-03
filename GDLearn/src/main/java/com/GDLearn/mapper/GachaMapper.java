package com.GDLearn.mapper;

import com.GDLearn.entity.Gacha;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GachaMapper extends BaseMapper<Gacha> {
    //获取数据库5星角色的id集合
    @Select("SELECT * FROM m_gacha_char WHERE rare = 5 AND islimit=0;")
    List<Integer> getCharUnlimit5starsIds();

    //获取指定id的角色
    @Select("SELECT * FROM m_gacha_char WHERE id = #{id};")
    Gacha getCharById(int id);

    //获取数据库4星角色的id集合
    @Select("SELECT * FROM m_gacha_char WHERE rare = 4 AND islimit=0;")
    List<Integer> getChar4starsIds();

    //获取数据库4星武器的id集合
    @Select("SELECT * FROM m_gacha_weapon WHERE rare = 4 AND islimit=0;")
    List<Integer> getWeapon4starsIds();

    //获取指定id的武器
    @Select("SELECT * FROM m_gacha_weapon WHERE id = #{id};")
    Gacha getWeaponById(int id);

    //获取数据库3星武器的id集合
    @Select("SELECT * FROM m_gacha_weapon WHERE rare = 3 AND islimit=0;")
    List<Integer> getWeapon3starsIds();
}

package com.kids_clothing.seeder;

import com.kids_clothing.Repository.SizeDao;
import com.kids_clothing.Repository.TyperSizeDao;
import com.kids_clothing.entity.Size;
import com.kids_clothing.entity.Typesize;

import java.util.ArrayList;
import java.util.List;

public class SizeSeeder implements Seeder {
    @Override
    public void seed() {
        SizeDao sizeDao = BeanUtil.getBean(SizeDao.class);
        TyperSizeDao typerSizeDao = BeanUtil.getBean(TyperSizeDao.class);

        if (!typerSizeDao.findByName("size áo").isPresent()) {
            Typesize typesize = new Typesize();
            typesize.setName("size áo");

            typerSizeDao.save(typesize);

            List<Size> sizes = new ArrayList<>();

            String[] nameS = new String[]{"S", "M", "L", "X", "XL", "XXL"};
            for (String name : nameS) {
                Size size = new Size();
                size.setName(name);
                size.setIdtypesize(typesize.getId());
                sizes.add(size);
            }
            sizeDao.saveAll(sizes);
        }
        if (!typerSizeDao.findByName("size quần").isPresent()) {
            Typesize typesize = new Typesize();
            typesize.setName("size quần");

            typerSizeDao.save(typesize);

            List<Size> sizes = new ArrayList<>();

            String[] nameS = new String[]{"27", "28", "29", "30", "31", "32"};
            for (String name : nameS) {
                Size size = new Size();
                size.setName(name);
                size.setIdtypesize(typesize.getId());
                sizes.add(size);
            }
            sizeDao.saveAll(sizes);
        }

    }
}

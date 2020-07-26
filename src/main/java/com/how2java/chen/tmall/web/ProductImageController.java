package com.how2java.chen.tmall.web;

import com.google.common.collect.Lists;
import com.how2java.chen.tmall.configure.PictureEnum;
import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.pojo.ProductImage;
import com.how2java.chen.tmall.service.ProductImageService;
import com.how2java.chen.tmall.service.ProductService;
import com.how2java.chen.tmall.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-25 18:41:58
 */

@RestController
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductService productService;


    @GetMapping("/products/{pid}/productImages")
    public List<ProductImage> list(@PathVariable("pid") int pid, String type) {

        List<ProductImage> productImages = Lists.newArrayList();
        Product product = productService.get(pid);

        if (Objects.equals(PictureEnum.TYPE_SINGLE.getKey(), type)) {
            productImages = productImageService.listSingleProductImages(product);
        }

        if (Objects.equals(PictureEnum.TYPE_DETAIL.getKey(), type)) {
            productImages = productImageService.listDetailProductImages(product);
        }


        return productImages;
    }


    @PostMapping("/productImages")
    public Object add(int pid, String type, MultipartFile image, HttpServletRequest request) {


        ProductImage bean = new ProductImage();
        Product product = productService.get(pid);
        bean.setProduct(product);
        bean.setType(type);
        bean.setPid(pid);
        
        productImageService.add(bean);
        String folder = "img/";

        boolean isSingle = PictureEnum.TYPE_SINGLE.getKey().equals(bean.getType());

        if (isSingle) {
            folder += "productSingle";
        } else {
            folder += "productDetail";
        }
        File imageFolder = new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder, bean.getId() + ".jpg");
        String fileName = file.getName();
        if (!file.getParentFile().exists()) {

            file.getParentFile().mkdirs();
        }
        try {
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isSingle) {
            String imageFolder_small = request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle = request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }

        return bean;

    }

    @DeleteMapping("/productImages/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        ProductImage bean = productImageService.get(id);
        productImageService.delete(id);

        String folder = "img/";
        boolean isSingle = PictureEnum.TYPE_SINGLE.getKey().equals(bean.getType());

        if (isSingle) {

            folder += "productSingle";
        } else {

            folder += "productDetail";
        }

        File imageFolder = new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder, bean.getId() + ".jpg");
        String fileName = file.getName();
        file.delete();
        if (isSingle) {
            String imageFolder_small = request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle = request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.delete();
            f_middle.delete();
        }

        return null;
    }


}

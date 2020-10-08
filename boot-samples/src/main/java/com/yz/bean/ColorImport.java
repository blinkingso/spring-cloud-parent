package com.yz.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

@Slf4j
public class ColorImport implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        log.info("selectImports Color hhh");
        return new String[]{Color.class.getName()};
    }
}

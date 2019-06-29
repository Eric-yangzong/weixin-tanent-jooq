package bdhb.usershiro.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bdhanbang.base.common.ApiResult;
import com.bdhanbang.base.exception.BusinessException;
import com.bdhanbang.base.message.CommonMessage;
import com.generator.tables.SysImage;
import com.generator.tables.pojos.SysImageEntity;
import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.common.AppCommon;
import bdhb.usershiro.common.CurrentUser;
import bdhb.usershiro.service.TableService;
import net.coobird.thumbnailator.Thumbnails;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/sys_image")
public class SysImageController {

	@Value("${image.save.path}")
	String imageSavePath;

	@Autowired
	private TableService sysImageService;

	@RequestMapping(method = RequestMethod.POST)
	public ApiResult<SysImageEntity> singleFileUpload(@RequestParam("upload") MultipartFile file,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) throws IllegalStateException, IOException {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<SysImageEntity> apiResult = new ApiResult<>();

		if (Objects.isNull(imageSavePath)) {
			throw new BusinessException("20000", "图片保存路径不能为空");
		}

		File saveFile = new File(imageSavePath);

		if (!saveFile.exists()) {
			throw new BusinessException("20000", String.format("图片保存路径【%s】不存在。", imageSavePath));
		}

		String fileName = file.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf("."));

		SysImageEntity sysImageEntity = new SysImageEntity();

		sysImageEntity.setImageId(UUID.randomUUID());
		sysImageEntity.setImageName(fileName);
		sysImageEntity.setThumbnail(sysImageEntity.getImageId());
		sysImageEntity.setImagePath(imageSavePath);
		sysImageEntity.setGraph(UUID.randomUUID());
		sysImageEntity.setUpdateFullName(currentUser.getFullName());
		sysImageEntity.setUpdateTime(LocalDateTime.now());
		sysImageEntity.setImageType(fileType);

		String saveFileName = String.format("%s/%s%s", imageSavePath, sysImageEntity.getGraph(), fileType);
		String thumbnailsFile = String.format("%s/%s%s", imageSavePath, sysImageEntity.getThumbnail(), fileType);

		saveFile = new File(saveFileName);

		if (file.isEmpty()) {
			throw new BusinessException("20000", "上传的文件为空");
		} else {
			file.transferTo(saveFile);
			Thumbnails.of(saveFile).size(40, 40).toFile(thumbnailsFile);
		}

		sysImageService.insertEntity(realSchema, SysImage.class, sysImageEntity);

		apiResult.setData(sysImageEntity);

		return apiResult;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<SysImageEntity> getEntity(@PathVariable("id") String id,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;
		ApiResult<SysImageEntity> apiResult = new ApiResult<>();

		SysImageEntity sysImageEntity = sysImageService.getEntity(realSchema, SysImage.class, SysImageEntity.class, id);

		apiResult.setData(sysImageEntity);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

}

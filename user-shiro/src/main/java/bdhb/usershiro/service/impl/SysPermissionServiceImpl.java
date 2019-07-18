package bdhb.usershiro.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import com.bdhanbang.base.jooq.GenSchema;
import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.generator.tables.SysPermission;
import com.generator.tables.SysRole;
import com.generator.tables.pojos.SysPermissionEntity;

import bdhb.usershiro.common.AppCommon;
import bdhb.usershiro.service.SysPermissionService;
import bdhb.usershiro.vo.Menu;

@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission, SysPermissionEntity>
		implements SysPermissionService {

	@Override
	public List<Menu> getMenus(String tenantId, String[] roles) {

		GenSchema schema = new GenSchema(String.format("%s%s", tenantId, AppCommon.scheam));

		SysRole sysRole = new SysRole();
		SysPermission sysPermission = new SysPermission();

		sysRole.setSchema(schema);
		sysPermission.setSchema(schema);

		List<SysPermissionEntity> sysPermissionEntitys = dsl.select(sysPermission.fields()).from(sysPermission)
				.where(DSL.exists(dsl.select(sysRole.fields()).from(sysRole)
						.where(DSL.cast(sysPermission.ID, String.class)
								.eq(DSL.function("any", String.class, sysRole.PERMISSIONS)))
						.and(sysRole.ROLE_CODE.in(roles))))
				.fetchInto(SysPermissionEntity.class);

		List<Menu> menus = sysPermissionEntitys.stream().filter(x -> Objects.isNull(x.getParentId())).map(x -> {
			Menu menu = new Menu();

			menu.setId(x.getId());
			menu.setParentId(x.getParentId());
			menu.setResName(x.getResName());
			menu.setUrl(x.getUrl());
			menu.setIcon(x.getIcon());
			menu.setQsort(Objects.isNull(x.getQsort()) ? 0 : x.getQsort());

			return menu;
		}).collect(Collectors.toList());

		menus.sort((x, y) -> x.getQsort() - y.getQsort());

		menus.forEach(x -> {

			List<Menu> children = sysPermissionEntitys.stream().filter(y -> x.getId().equals(y.getParentId()))
					.map(m -> {
						Menu menu = new Menu();

						menu.setId(m.getId());
						menu.setParentId(m.getParentId());
						menu.setResName(m.getResName());
						menu.setUrl(m.getUrl());
						menu.setIcon(m.getIcon());
						menu.setQsort(Objects.isNull(m.getQsort()) ? 0 : m.getQsort());

						return menu;
					}).collect(Collectors.toList());

			children.sort((q, o) -> q.getQsort() - o.getQsort());

			x.setChildren(children);

		});

		return menus;
	}
}

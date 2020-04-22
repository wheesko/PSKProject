import React from 'react';
import history from '../../history';
import { Menu } from 'antd';

import {
	CALENDAR_MENU_ITEM_NAME, CREATE_NEW_TOPIC_MENU_ITEM_NAME, INFO_MENU_ITEM_NAME, KEY_CALENDAR, KEY_INFO, KEY_MEMBERS,
	KEY_MY_CALENDAR, KEY_NEW_TOPIC,
	KEY_PROFILE,
	KEY_TEAMS, KEY_TOPIC_TREE, KEY_TOPICS, MY_TEAM_CALENDAR_MENU_ITEM_NAME, TEAM_MEMBERS_MENU_ITEM_NAME,
	TEAMS_MENU_ITEM_NAME, TOPIC_TREE_MENU_ITEM_NAME, TOPICS_MENU_ITEM_NAME,
	USER_MENU_ITEM_NAME
} from '../../Constants';

import {
	UserOutlined,
	CalendarOutlined,
	TeamOutlined,
	InfoCircleOutlined,
	SolutionOutlined,
	TagsOutlined,
	DeploymentUnitOutlined,
	PlusOutlined
} from '@ant-design/icons';

import { Link } from 'react-router-dom';
import SubMenu from 'antd/lib/menu/SubMenu';

const SideMenu: React.FunctionComponent<{}> = () => {

	return (
		<Menu theme="dark" mode="inline"
			defaultSelectedKeys={history.location.pathname.split('/')}
			defaultOpenKeys={history.location.pathname.split('/')}
			className={'menuList'}
		>
			<Menu.Item key={KEY_PROFILE}>
				<Link to={`/${KEY_PROFILE}`}>
					<UserOutlined/>
					<span className="nav-text">{USER_MENU_ITEM_NAME}</span>
				</Link>
			</Menu.Item>
			<Menu.Item key={KEY_MY_CALENDAR}>
				<Link to={`/${KEY_MY_CALENDAR}`}>
					<CalendarOutlined/>
					<span className="nav-text">{CALENDAR_MENU_ITEM_NAME}</span>
				</Link>
			</Menu.Item>
			<SubMenu
				key={KEY_TEAMS}
				title={
					<span>
						<TeamOutlined/>
						<span className="nav-text">{TEAMS_MENU_ITEM_NAME}</span>
					</span>
				}
			>
				<Menu.Item key={KEY_CALENDAR}>
					<Link to={`/${KEY_TEAMS}/${KEY_CALENDAR}`}>
						<TeamOutlined/>
						<span className="nav-text">{MY_TEAM_CALENDAR_MENU_ITEM_NAME}</span>
					</Link>
				</Menu.Item>
				<Menu.Item key={KEY_MEMBERS}>
					<Link to={`/${KEY_TEAMS}/${KEY_MEMBERS}`}>
						<SolutionOutlined/>
						<span className="nav-text">{TEAM_MEMBERS_MENU_ITEM_NAME}</span>
					</Link>
				</Menu.Item>
				<Menu.Item key={KEY_INFO}>
					<Link to={`/${KEY_TEAMS}/${KEY_INFO}`}>
						<InfoCircleOutlined/>
						<span className="nav-text">{INFO_MENU_ITEM_NAME}</span>
					</Link>
				</Menu.Item>
			</SubMenu>
			<SubMenu
				key={KEY_TOPICS}
				title={
					<span>
						<TagsOutlined/>
						<span className="nav-text">{TOPICS_MENU_ITEM_NAME}</span>
					</span>
				}
			>
				<Menu.Item key={KEY_TOPIC_TREE}>
					<Link to={`/${KEY_TOPICS}/${KEY_TOPIC_TREE}`}>
						<DeploymentUnitOutlined/>
						<span className="nav-text">{TOPIC_TREE_MENU_ITEM_NAME}</span>
					</Link>
				</Menu.Item>
				<Menu.Item key={KEY_NEW_TOPIC}>
					<Link to={`/${KEY_TOPICS}/${KEY_NEW_TOPIC}`}>
						<PlusOutlined/>
						<span className="nav-text">{CREATE_NEW_TOPIC_MENU_ITEM_NAME}</span>
					</Link>
				</Menu.Item>
			</SubMenu>
		</Menu>
	);
};

export { SideMenu };
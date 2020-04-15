import React from 'react';
import { Layout, Menu } from 'antd';
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
import { Route, Link, Switch } from 'react-router-dom';
import {
	TEAM_NAME,
	USER_MENU_ITEM_NAME,
	CALENDAR_MENU_ITEM_NAME,
	INFO_MENU_ITEM_NAME,
	TEAMS_MENU_ITEM_NAME,
	MY_TEAM_CALENDAR_MENU_ITEM_NAME,
	TEAM_MEMBERS_MENU_ITEM_NAME,
	KEY_TEAMS,
	KEY_CALENDAR,
	KEY_MEMBERS,
	KEY_INFO,
	KEY_MY_CALENDAR,
	KEY_PROFILE,
	KEY_TEAM,
	KEY_TOPICS,
	TOPICS_MENU_ITEM_NAME,
	KEY_TOPIC_TREE,
	TOPIC_TREE_MENU_ITEM_NAME,
	KEY_NEW_TOPIC,
	CREATE_NEW_TOPIC_MENU_ITEM_NAME
} from './Constants';

import history from './history';

import CalendarView from './pages/private/calendar-view';
import LandingPage from './pages/private/landing';

import './App.css';
import InfoView from './pages/private/info-view';
import TeamCalendarView from './pages/private/team-calendar-view';
import TeamMembersView from './pages/private/team-members-view';
import TopicTreeView from './pages/private/topic-tree-view';
import NewTopicView from './pages/private/new-topic-view';
import ProfileView from './pages/private/profile-view';

const { Content, Footer, Sider } = Layout;

const { SubMenu } = Menu;
const App: React.FunctionComponent<{}> = () => {
	return (
		<div className="App">
			<Layout id="root-layout">
				<Sider id="sider">
					<div className="logo"/>
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
				</Sider>
				< Layout
					id="main-content-layout">
					< Content>
						< Switch>
							< Route
								exact
								path="/"
								component={LandingPage}
							/>
							<Route exact path={`/${KEY_MY_CALENDAR}`} component={CalendarView}/>
							< Route
								exact
								path={`/${KEY_MY_CALENDAR}/:year/:month/:day`}/>
							< Route
								exact
								path={`/${KEY_PROFILE}`}
								component={ProfileView}
							/>
							<Route exact path={`/${KEY_TEAMS}/${KEY_CALENDAR}`} component={TeamCalendarView}/>

							<Route exact path={`/${KEY_TEAMS}/${KEY_MEMBERS}`} component={TeamMembersView}/>
							<Route exact path={`/${KEY_TEAMS}/${KEY_CALENDAR}`}/>
							<Route exact path={`/${KEY_TEAMS}/${KEY_INFO}`} component={InfoView}/>
							<Route exact path={`/${KEY_TOPICS}/${KEY_TOPIC_TREE}`} component={TopicTreeView}/>
							<Route exact path={`/${KEY_TOPICS}/${KEY_NEW_TOPIC}`} component={NewTopicView}/>
						</Switch>
					</Content>
					<Footer>Powered By: {TEAM_NAME}</Footer>
				</Layout>
			</Layout>
		</div>
	);
};

export default App;

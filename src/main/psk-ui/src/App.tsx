import React, {useState} from 'react';

import {Layout, Menu} from 'antd';
import {UserOutlined, CalendarOutlined, TeamOutlined, InfoCircleOutlined, SolutionOutlined} from '@ant-design/icons';
import {Route, Link, Switch} from 'react-router-dom';
import {
	TEAM_NAME,
	USER_MENU_ITEM_NAME,
	CALENDAR_MENU_ITEM_NAME,
	INFO_MENU_ITEM_NAME,
	TEAMS_MENU_ITEM_NAME,
	MY_TEAM_CALENDAR_MENU_ITEM_NAME,
	TEAM_MEMBERS_MENU_ITEM_NAME, KEY_TEAMS, KEY_CALENDAR, KEY_MEMBERS, KEY_INFO, KEY_MY_CALENDAR, KEY_PROFILE, KEY_TEAM
} from './Constants';

import history from "./history";

import CalendarView from './pages/public/calendar-view';
import LandingPage from './pages/public/landing';

import './App.css';
import InfoView from './pages/public/info-view';
import TeamCalendarView from './pages/public/team-calendar-view';
import TeamMembersView from "./pages/public/team-members-view";

const {Content, Footer, Sider} = Layout;

const {SubMenu} = Menu;
const App: React.FunctionComponent<{}> = () => {
	return (
		<div className="App">
			<Layout id="root-layout">
				<Sider id="sider">
					<div className="logo"/>
					<Menu theme="dark" mode="inline"
						  defaultSelectedKeys={history.location.pathname.split('/')}
						  defaultOpenKeys={history.location.pathname.split('/')}
						  className={"menuList"}
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
								component={LandingPage}
							/>
							<Route exact path={`/${KEY_TEAMS}/${KEY_CALENDAR}`} component={TeamCalendarView}/>

							<Route exact path={`/${KEY_TEAMS}/${KEY_MEMBERS}`} component={TeamMembersView}/>
							<Route exact path={`/${KEY_TEAMS}/${KEY_CALENDAR}`}/>
							<Route exact path={`/${KEY_TEAMS}/${KEY_INFO}`} component={InfoView}/>

						</Switch>
					</Content>
					<Footer>Powered By: {TEAM_NAME}</Footer>
				</Layout>
			</Layout>
		</div>
	);
};

export default App;

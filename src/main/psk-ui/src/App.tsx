import React from 'react';
import { Layout, Button } from 'antd';

import {
	TEAM_NAME,
} from './constants/otherConstants';

import './App.css';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from './redux';
import { LoginPage } from './pages/public/login';
import { SideMenu } from './components/side-menu/side-menu';
import { Routes } from './routes/routes';
import { thunkLogout } from './thunks';
import history from './history';
import notificationService, { NotificationType } from './service/notification-service';

const { Content, Footer, Sider } = Layout;

const App: React.FunctionComponent<{}> = () => {
	// TS infers `isOn` is boolean
	const user = useSelector( (state: RootState) => state.user);

	const dispatch = useDispatch();

	const logout = () => {
		dispatch(thunkLogout());
		// when logging out, change current url to '/'
		history.push('/');
		notificationService.notify( {
			notificationType: NotificationType.SUCCESS,
			message: 'Logged out successfully'
		});
	};
	
	return (
		!user.loggedIn
			? <LoginPage/>
			: <div className="App">
				<Layout id="root-layout">
					<Sider id="sider">
						<SideMenu/>
						<Button onClick={logout} className="logout-button">
							Logout
						</Button>
					</Sider>
					<Layout
						id="main-content-layout">
						<Content>
							<Routes/>
						</Content>
						<Footer>Powered By: {TEAM_NAME}</Footer>
					</Layout>
				</Layout>
			</div>
	);
};

export { App };

import React from 'react';
import { Button, Layout } from 'antd';

import './App.css';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from './redux';
import { LoginPage } from './pages/public/login';
import { SideMenu } from './components/side-menu/side-menu';
import { Routes } from './routes/routes';
import { thunkLogout } from './thunks';
import history from './history';
import notificationService, { NotificationType } from './service/notification-service';
import { Authority } from './models/authority';
import { RegisterPage } from './pages/public/register';

const { Content, Sider } = Layout;

const App: React.FunctionComponent<{}> = () => {
	// TS infers `isOn` is boolean
	const user = useSelector((state: RootState) => state.user);

	const dispatch = useDispatch();

	const logout = () => {
		dispatch(thunkLogout());
		// when logging out, change current url to '/'
		history.push('/');
		notificationService.notify({
			notificationType: NotificationType.SUCCESS,
			message: 'Logged out successfully'
		});
	};

	return (
		!user.loggedIn
			? <LoginPage/>
			: user.authority === Authority.FRESHMAN
				? <RegisterPage/>
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
						</Layout>
					</Layout>
				</div>
	);
};

export { App };

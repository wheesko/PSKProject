import React from 'react';
import { Layout, Button } from 'antd';

import {
	TEAM_NAME,
} from './Constants';

import './App.css';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from './redux';
import { LoginPage } from './pages/public/login';
import { SideMenu } from './components/side-menu/side-menu';
import { Routes } from './routes/routes';
import { userLogout } from './redux/user/actions';

const { Content, Footer, Sider } = Layout;

const App: React.FunctionComponent<{}> = () => {
	// TS infers `isOn` is boolean
	const user = useSelector( (state: RootState) => state.user);

	const dispatch = useDispatch();

	const logout = () => {
		dispatch(userLogout());
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

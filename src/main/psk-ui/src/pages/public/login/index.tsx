import React from 'react';
import { Button, Col, Form, Input, Layout, Row, Typography } from 'antd';
import { TEAM_NAME } from '../../../Constants';
import { useDispatch } from 'react-redux';
import { thunkLogin } from '../../../thunks';

const layout = {
	labelCol: { span: 8 },
	wrapperCol: { span: 16 },
};
const tailLayout = {
	wrapperCol: { offset: 8, span: 16 },
};
const { Content, Footer } = Layout;
const { Title } = Typography;

const LoginPage: React.FunctionComponent<{}> = () => {
	const dispatch = useDispatch();
	const [form] = Form.useForm();
	const onFinish = () => {
		dispatch(thunkLogin({
			userName: form.getFieldValue('username'),
			password: form.getFieldValue('password')
		}));
	};

	const onFinishFailed = () => {
		//TODO: will display an alert for incorrect log in when proper error response is implemented
		console.log('Failed:');
	};

	//TODO: layout is a little bid too wide and to high
	return <>
		<Layout id="root-layout">
			<Content>
				<Row gutter={[48, 48]} justify="center">
					<Col xs={12}>
						<Title>Welcome to PSK_123 Calendar app!</Title>
					</Col>
				</Row>
				<Row gutter={[48, 48]} justify="center">
					<Col xs={12}>
						<Title level={2}>Please log in to continue</Title>
					</Col>
				</Row>
				<Form.Provider onFormFinish={onFinish}>
					<Form
						form={form}
						{...layout}
						name="basic"
						// onFinish={onFinish}
						onFinishFailed={onFinishFailed}
					>
						<Row gutter={[48, 12]} justify="center">
							<Col xs={12}>
								<Form.Item
									label="Username"
									name="username"
									rules={[{ required: true, message: 'Please input your username!' }]}
								>
									<Input/>
								</Form.Item>
							</Col>
						</Row>
						<Row gutter={[48, 12]} justify="center">
							<Col xs={12}>
								<Form.Item
									label="Password"
									name="password"
									rules={[{ required: true, message: 'Please input your password!' }]}
								>
									<Input.Password/>
								</Form.Item>
							</Col>
						</Row>
						<Row gutter={[48, 12]} justify="center">
							<Col xs={12}>
								<Form.Item {...tailLayout}>
									<Button type="primary" htmlType="submit">
										Submit
									</Button>
								</Form.Item>
							</Col>
						</Row>
					</Form>
				</Form.Provider>
			</Content>
			<Footer>Powered By: {TEAM_NAME}</Footer>
		</Layout>
	</>;
};

export { LoginPage };
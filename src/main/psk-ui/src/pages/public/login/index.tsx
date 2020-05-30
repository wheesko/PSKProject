import React from 'react';
import {Button, Card, Col, Form, Input, Layout, Row, Typography} from 'antd';
import {TEAM_NAME} from '../../../constants/otherConstants';
import {useDispatch} from 'react-redux';
import {thunkLogin} from '../../../thunks';
import '../../../App.css';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faCalendarAlt} from "@fortawesome/free-regular-svg-icons";
import './LoginStyles.css';

const layout = {
	labelCol: {span: 24},
	wrapperCol: {span: 24},
};
const tailLayout = {
	wrapperCol: { span: 24},
};
const {Content, Footer} = Layout;
const {Title} = Typography;

const LoginPage: React.FunctionComponent<{}> = () => {
	const dispatch = useDispatch();
	const [form] = Form.useForm();
	const onFinish = () => {
		dispatch(thunkLogin({
			email: form.getFieldValue('email'),
			password: form.getFieldValue('password')
		}));
	};

	const onFinishFailed = () => {
		//TODO: will display an alert for incorrect log in when proper error response is implemented
		console.log('Failed:');
	};

	function renderLoginForm(): React.ReactNode {
		return (
			<Form.Provider onFormFinish={onFinish}>
				<Form
					form={form}
					{...layout}
					name="basic"
					// onFinish={onFinish}
					onFinishFailed={onFinishFailed}
				>
					<Form.Item
						label="Email"
						name="email"
						rules={[{required: true, message: 'Please input your email!'}]}
					>
						<Input/>
					</Form.Item>
					<Form.Item
						label="Password"
						name="password"
						rules={[{required: true, message: 'Please input your password!'}]}
					>
						<Input.Password/>
					</Form.Item>
					<Form.Item {...tailLayout}>
						<Button type="primary" htmlType="submit" id={'login-button'}>
							Submit
						</Button>
					</Form.Item>
				</Form>
			</Form.Provider>
		);
	}

	//TODO: layout is a little bid too wide and to high
	return <>
		<Row>
			<Col span={10}>
				<Card bordered={false} className='login-left-background'>
					<FontAwesomeIcon icon={faCalendarAlt} size='10x' color={'white'}/>
				</Card>
			</Col>
			<Col span={14}>
				<Layout id="root-layout" >
					<Content id="login-wrapper">
						<Row gutter={[0, 48]} justify="center" align="middle" id="login-title-row">
							<Col xs={12}>
								<Title>Welcome to PSK_123 Calendar app!</Title>
							</Col>
						</Row>
						<Row gutter={[0, 48]} justify="center">
							<Col xs={12}>
								<Title level={2}>Please log in to continue</Title>
							</Col>
						</Row>
						<Row justify="center">
							<Col xs={12}>
								<Card id="login-form-card">
								{renderLoginForm()}
								</Card>
							</Col>
						</Row>
					</Content>
					<Footer>Powered By: {TEAM_NAME}</Footer>
				</Layout>
			</Col>
		</Row>
	</>;
};

export {LoginPage};
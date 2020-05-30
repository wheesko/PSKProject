import React, {useState} from 'react';
import {Button, Col, Form, Input, Layout, Row, Typography, Tooltip, Card} from 'antd';
import {TEAM_NAME} from '../../../constants/otherConstants';
import {useDispatch} from 'react-redux';
import {thunkLogout, thunkRegister} from '../../../thunks';
import '../../../App.css';
import {Link} from 'react-router-dom';
import history from '../../../history';
import notificationService, {NotificationType} from '../../../service/notification-service';
import {QuestionCircleOutlined, ArrowLeftOutlined} from '@ant-design/icons';
import {
	confirmPasswordMissing,
	nameMissing, passwordMissing,
	passwordRegexErrorDescription,
	passwordRegexErrorMessage, passwordsNotMatchingMessage, surnameMissing
} from '../../../constants/registerConstants';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEdit} from "@fortawesome/free-regular-svg-icons";
import {faUndo} from "@fortawesome/free-solid-svg-icons";
import './RegisterStyles.css'

const layout = {
	labelCol: {span: 24},
	wrapperCol: {span: 24},
};
const {Content, Footer} = Layout;
const {Title} = Typography;

const RegisterPage: React.FunctionComponent<{}> = () => {

	const passwordRegExp = RegExp('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,}$');
	const dispatch = useDispatch();
	const [form] = Form.useForm();
	const [errorMessage, setErrorMessage] = useState('');
	const [errorDescription, setErrorDescription] = useState('');
	const onFinish = () => {
		dispatch(thunkRegister({
			name: form.getFieldValue('name'),
			surname: form.getFieldValue('surname'),
			password: form.getFieldValue('password')
		}));
		history.push('/');
	};
	const logout = () => {
		dispatch(thunkLogout());
		// when logging out, change current url to '/'
		history.push('/');
		notificationService.notify({
			notificationType: NotificationType.SUCCESS,
			message: 'Logged out successfully'
		});
	};

	const onFinishFailed = () => {
		//TODO: will display an alert for incorrect log in when proper error response is implemented
		notificationService.notify({
			notificationType: NotificationType.ERROR,
			message: errorMessage,
			description: errorDescription
		});
	};

	function renderRegisterForm(): React.ReactNode {
		return (
			<Form.Provider>
				<Form
					form={form}
					{...layout}
					name="basic"
					onFinish={onFinish}
					onFinishFailed={onFinishFailed}
				>
					<Form.Item
						label="Name"
						name="name"
						rules={[{required: true, message: nameMissing}]}
					>
						<Input/>
					</Form.Item>
					<Form.Item
						label="Surname"
						name="surname"
						rules={[{required: true, message: surnameMissing}]}
					>
						<Input/>
					</Form.Item>
					<Form.Item
						label={<span>Password&nbsp;
							<Tooltip
								title={passwordRegexErrorDescription}>
								<QuestionCircleOutlined/>
							</Tooltip>
						</span>}
						name="password"
						rules={[{required: true, message: passwordMissing}]}
					>
						<Input.Password/>
					</Form.Item>
					<Form.Item
						label="Confirm password"
						name="confirmPass"
						dependencies={['password']}
						rules={[
							{
								required: true,
								message: confirmPasswordMissing,
							},
							({getFieldValue}) => ({
								validator(rule, value) {
									if (!value || getFieldValue('password') === value) {
										if (!passwordRegExp.test(value)) {
											setErrorMessage(passwordRegexErrorMessage);
											setErrorDescription(passwordRegexErrorDescription);
											return Promise.reject(passwordRegexErrorMessage);
										} else
											return Promise.resolve();
									}
									return Promise.reject(passwordsNotMatchingMessage);
								},
							}),
						]}
					>
						<Input.Password/>
					</Form.Item>
					<Form.Item {...layout}>
						<Button type="primary" htmlType="submit" id='register-button'>
							Submit
						</Button>
					</Form.Item>
					<Form.Item {...layout}>
						<Link to={'/'}>
							<Tooltip title={'Back to login'}>
								<Button onClick={logout} shape="round" size='large'>
									<ArrowLeftOutlined />
									{/*<FontAwesomeIcon icon={faUndo}/>*/}
								</Button>
							</Tooltip>
						</Link>
					</Form.Item>
				</Form>
			</Form.Provider>
		);
	}

	return <>
		<Row>
			<Col span={10}>
				<Card bordered={false} className='register-left-background'>
					<FontAwesomeIcon icon={faEdit} size='10x' color={'white'}/>
				</Card>
			</Col>
			<Col span={14}>
				<Layout id="root-layout">
					<Content id="login-wrapper">
						<Row gutter={[0, 48]} justify="center" id='register-title-row'>
							<Col xs={12}>
								<Title level={2}>Finish your registration to start using the application</Title>
							</Col>
						</Row>
						<Row justify="center">
							<Col xs={12}>
								<Card id="register-form-card">
									{renderRegisterForm()}
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

export {RegisterPage};
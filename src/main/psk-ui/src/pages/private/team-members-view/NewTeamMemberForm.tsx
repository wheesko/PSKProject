import React from 'react';

import { Form, Input, Button, Card, Tooltip, } from 'antd';
import { useDispatch } from 'react-redux';
import {
	CREATE_NEW_EMPLOYEE,
	EMPLOYEE_EMAIL,
	EMPLOYEE_EMAIL_REQUIRED, EMPLOYEE_GOALS,
	EMPLOYEE_NAME,
	EMPLOYEE_NAME_REQUIRED,
	EMPLOYEE_QUARTER_CONSTRAINT,
	EMPLOYEE_ROLE,
	EMPLOYEE_SURNAME,
	EMPLOYEE_SURNAME_REQUIRED, GOALS_INFO, QUARTER_CONSTRAINT_INFO,
} from '../../../constants/employeeConstants';
import { QuestionCircleOutlined } from '@ant-design/icons';
import {thunkAddEmployee} from "../../../thunks";

const formItemLayout = {
	labelCol: {
		xs: { span: 24 },
		sm: { span: 8 }
	},
	wrapperCol: {
		xs: { span: 24 },
		sm: { span: 16 }
	}
};

const NewTeamMemberForm: React.FunctionComponent<{}> = () => {
	const [form] = Form.useForm();
	const dispatch = useDispatch();

	return (
		<Card>
			<Form.Provider
				onFormFinish={() => {
					dispatch(thunkAddEmployee({
						id: Math.floor((Math.random() * 1000) + 6), // the id should be set in the backend
						name: form.getFieldValue('name') + ' ' + form.getFieldValue('surname'),
						team: 'PSK_123',
						quarterConstraint: form.getFieldValue('quarterConstraint'),
						role: form.getFieldValue('role') === undefined ? '': form.getFieldValue('role'),
						goals: form.getFieldValue('goals') === undefined ? [] : form.getFieldValue('goals')
					}));
					form.resetFields();
				}}
			>
				<Form
					form={form}
					{...formItemLayout}
					initialValues={{ remember: true }}
					name="learningEventForm"
				>
					<Form.Item
						label={EMPLOYEE_NAME}
						name="name"
						rules={[{ required: true, message: EMPLOYEE_NAME_REQUIRED }]}
					>
						<Input allowClear/>
					</Form.Item>
					<Form.Item
						label={EMPLOYEE_SURNAME}
						name="surname"
						rules={[{ required: true, message: EMPLOYEE_SURNAME_REQUIRED }]}
					>
						<Input allowClear/>
					</Form.Item>
					<Form.Item
						label={EMPLOYEE_EMAIL}
						name="email"
						rules={[{ required: true, message: EMPLOYEE_EMAIL_REQUIRED, type: 'email' }]}
					>
						<Input allowClear/>
					</Form.Item>
					<Form.Item
						label={EMPLOYEE_ROLE}
						name="role"
					>
						<Input/>
					</Form.Item>
					<Form.Item
						name="quarterConstraint"
						label={
							<span>
								{EMPLOYEE_QUARTER_CONSTRAINT}&nbsp;
								<Tooltip title={QUARTER_CONSTRAINT_INFO}>
									<QuestionCircleOutlined/>
								</Tooltip>
							</span>}>
						<Input/>
					</Form.Item>
					<Form.Item
						name="goals"
						label={
							<span>
								{EMPLOYEE_GOALS}&nbsp;
								<Tooltip title={GOALS_INFO}>
									<QuestionCircleOutlined/>
								</Tooltip>
							</span>}>
						<Input/>
					</Form.Item>
					<Form.Item
						wrapperCol={{
							xs: { span: 24, offset: 0 },
							sm: { span: 16, offset: 8 }
						}}
					>
						<Button
							className="event-form-buttons success"
							type="primary"
							htmlType="submit"
						>
							{CREATE_NEW_EMPLOYEE}
						</Button>
					</Form.Item>
				</Form>
			</Form.Provider>
		</Card>
	);
};

export { NewTeamMemberForm };

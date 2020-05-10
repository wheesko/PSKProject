import React from 'react';

import { Form, Input, Button, Alert, notification, } from 'antd';
import {
	EMPLOYEE_EMAIL,
	EMPLOYEE_EMAIL_REQUIRED,  INVITE_NEW_EMPLOYEE, SEND_INVITE_LINK,
} from '../../../constants/employeeConstants';
import './editable-table/EditableTableStyles.css';

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
	const openSuccessNotificationWithIcon = () => {
		notification['success']({
			message: 'Email invite sent!',
		});
	};
	const openErrorNotificationWithIcon = (errorMessage: string) => {
		notification['error']({
			message: 'Invite unsuccessful!',
			description: errorMessage
		});
	};
	return (
		<>
			<Alert
				type="info"
				showIcon
				message={SEND_INVITE_LINK}
				description={
					<>
						<p>Send out an invite link to a new employee. They will finish the registration with their
							personal data.</p>
						<span>After that, you will be able to:</span>
						<ul>
							<li>manage their learning day quarter constraint;</li>
							<li>role;</li>
							<li>goals;</li>
						</ul>
					</>
				}
				className="infoAlert"/>
			<Form.Provider
				onFormFinish={() => {
					form.resetFields();
					// send invite to provided email
					// check for some backend response
					//try...
					openSuccessNotificationWithIcon();
					//catch
					// openErrorNotificationWithIcon('errorMessage')
				}}
			>
				<Form
					form={form}
					{...formItemLayout}
					initialValues={{ remember: true }}
					name="learningEventForm"
				>
					<Form.Item
						label={EMPLOYEE_EMAIL}
						name="email"
						rules={[{ required: true, message: EMPLOYEE_EMAIL_REQUIRED, type: 'email' }]}
					>
						<Input allowClear/>
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
							{INVITE_NEW_EMPLOYEE}
						</Button>
					</Form.Item>
				</Form>
			</Form.Provider>
		</>
	);
};

export { NewTeamMemberForm };

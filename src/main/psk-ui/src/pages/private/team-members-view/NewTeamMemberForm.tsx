import React, { useState } from 'react';

import { Form, Input, Button, Alert, notification, Spin, } from 'antd';
import {
	EMPLOYEE_EMAIL,
	EMPLOYEE_EMAIL_REQUIRED, EMPLOYEE_ROLE, INVITE_NEW_EMPLOYEE, SEND_INVITE_LINK,
} from '../../../constants/employeeConstants';
import './editable-table/EditableTableStyles.css';
import workerService from '../../../api/worker-service';
import notificationService, { NotificationType } from '../../../service/notification-service';

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

interface NewTeamMemberFormProps {
	managerId: number;
}

const NewTeamMemberForm: React.FunctionComponent<NewTeamMemberFormProps> = (props: NewTeamMemberFormProps) => {
	const [isSending, setIsSending] = useState<boolean>(false);

	const [form] = Form.useForm();
	const openSuccessNotificationWithIcon = () => {
		notification['success']({
			message: 'Email invite sent!',
		});
	};

	function sendEmployeeInvite() {
		setIsSending(true);
		workerService.addEmployee({
			email: form.getFieldsValue()['email'],
			role: form.getFieldsValue()['role'],
			'managerId': props.managerId
		}).then(() => {
			setIsSending(false);
			openSuccessNotificationWithIcon();
			form.resetFields();
		}).catch((error) => {
			setIsSending(false);
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Failed to send invite!',
				description: error.toString()
			});
		});
	}

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
			<Spin spinning={isSending} size="small">
				<Form.Provider
					onFormFinish={sendEmployeeInvite}
				>
					<Form
						form={form}
						{...formItemLayout}
						initialValues={{ remember: true }}
						name="newTeamMemberForm"
					>
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
							rules={[{ required: true, message: EMPLOYEE_EMAIL_REQUIRED }]}
						>
							<Input allowClear placeholder={'e.g. Backend Engineer'}/>
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
			</Spin>
		</>
	);
};

export { NewTeamMemberForm };

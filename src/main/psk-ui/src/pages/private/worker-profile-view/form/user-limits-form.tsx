import React, { useEffect } from 'react';

import { Button, Card, Form, InputNumber, } from 'antd';

import notificationService, { NotificationType } from '../../../../service/notification-service';
import workerService from '../../../../api/worker-service';
import { UpdateLimitsRequest } from '../../../../api/model/update-limits-request';

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

interface EventFormProps {
    workerId: number;
    limitsQuarterly: number;
    limitsSequence: number;
    onSubmit: () => void;
}

const UserLimitsForm: React.FunctionComponent<EventFormProps> = (props: EventFormProps) => {
	const [form] = Form.useForm();

	const {
		workerId,
		onSubmit,
		limitsQuarterly,
		limitsSequence
	} = props;

	useEffect(() => {
       
		form.setFieldsValue({
			quarterlyLimit: limitsQuarterly,
			consecutiveLimit: limitsSequence
		});
       
	}, []);
	
	return (
		<Card title={'Update limits'}>
			<Form.Provider
				onFormFinish={name => {
					updateLimits({
						consecutiveLearningDayLimit: form.getFieldsValue()['consecutiveLimit'],
						quarterLearningDayLimit: form.getFieldsValue()['quarterlyLimit']
					}, workerId);
				}}
			>
				<Form
					form={form}
					{...formItemLayout}
					initialValues={{ remember: true }}
					name="learningEventForm"
				>
					<Form.Item
						label={'Quarterly limit'}
						name="quarterlyLimit"
						rules={[{ required: true, message: 'Field required' }]}
					>
						<InputNumber min={1} max={100}/>
					</Form.Item>
					<Form.Item
						label={'Consecutive limit'}
						name="consecutiveLimit"
						rules={[{ required: true, message: 'Field required' }]}
					>
						<InputNumber min={1} max={100}/>
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
                            Update limits
						</Button>
					</Form.Item>
				</Form>
			</Form.Provider>
		</Card>
	);

	function updateLimits(limits: UpdateLimitsRequest, id: number): Promise<void> {
		return workerService.updateLimits(limits, id).then(() => {
			notificationService.notify({
				notificationType: NotificationType.SUCCESS,
				message: 'Limits update'
			});
			onSubmit();
		})
			.catch(() => {
				notificationService.notify({
					notificationType: NotificationType.ERROR,
					message: 'Could not update limits'
				});
			});
	}
};

export { UserLimitsForm };

import React from 'react';

import { Button, Row, Col } from 'antd';

const LandingPage: React.FunctionComponent<{}> = () => {
	return (
		<div className="LandingPage" >
			<Row gutter={[48, 48]} type='flex' justify='center'>
				<Col xs={12}>
					Initial test page
				</Col>
			</Row>
			<Row gutter={[48, 48]} type='flex' justify='center'>
				<Col xs={12}>
					<Button onClick={handleClick} type='primary' block={true}>
						clicker
					</Button>
				</Col>
			</Row>
		</div>
	);
};

const handleClick = (): void => {
	console.log('click');
};

export default LandingPage;
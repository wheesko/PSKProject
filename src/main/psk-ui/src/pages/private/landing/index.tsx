import React from 'react';

import { Button, Row, Col } from 'antd';

const LandingPage: React.FunctionComponent<{}> = () => {
	return (
		<div className="LandingPage">
			<Row gutter={[48, 48]} justify="center">
				<Col xs={12}>
					Welcome to PSK_123 calendar app!
				</Col>
			</Row>
		</div>
	);
};

export { LandingPage };

import styled from 'styled-components';
import DefaultButton from './DefaultButton';

const ExtendedButton = styled(DefaultButton)`
  width: calc(100% + 2px);
  margin: -1px;
  border-radius: 0 0 8px 8px;
`;

export default ExtendedButton;

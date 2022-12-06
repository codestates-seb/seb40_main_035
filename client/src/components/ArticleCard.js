import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { ReactComponent as LikeIcon } from '../assets/image/like.svg';
import { ReactComponent as ViewsIcon } from '../assets/image/views.svg';
import yearMonthDate from '../utils/dateFormat';

const ArticleCardWrapper = styled(Link)`
  display: inline-block;
  width: 100%;
  height: 286px;
  border-radius: 25px;
  background-color: #ffffff;
  transition: 300ms ease-in-out;
  position: relative;

  &:hover {
    box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px;
    transform: translate(0, -5px);
  }

  &.closed {
    &:after {
      content: '';
      display: block;
      width: 100%;
      height: 100%;
      position: absolute;
      top: 0;
      left: 0;
      border-radius: 25px;
      box-shadow: inset 0 0 0 400px rgba(0, 0, 0, 0.15);
    }
  }

  article {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 100%;
    height: 100%;
    overflow: hidden;
    padding: 33px;
  }

  .article-header {
    margin-bottom: 20px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
    font-size: 18px;
    line-height: 24.52px;
    color: var(--black);
  }

  .project-info,
  .hashtag-list {
    margin-bottom: 4px;
    font-size: 15px;
    line-height: 18px;
    color: var(--grey-dark);
  }

  .project-info {
    font-weight: 500;

    p {
      margin-bottom: 4px;

      span {
        margin-right: 4px;
      }
    }

    p:last-child {
      margin-bottom: 20px;

      span:last-child {
        margin-left: 6px;

        &::before {
          content: '|';
          margin-right: 6px;
        }
      }
    }
  }

  .hashtag-list,
  .skill-list {
    overflow: hidden;

    li {
      display: inline-block;
      margin-right: 7px;
    }
  }

  .hashtag-list {
    height: 20px;

    li {
      font-weight: 400;
    }
  }

  .skill-list {
    height: 26px;

    li {
      height: 100%;
      padding: 4px 10px;
      border-radius: 13px;
      font-weight: 700;
      font-size: 13px;
      line-height: 17.71px;
      color: #fff;
      background-color: var(--purple);
    }
  }

  .project-summary {
    margin-bottom: 10px;
  }

  footer {
    display: flex;
    justify-content: space-between;
    color: var(--grey-dark);

    .author {
      width: 100%;
      font-weight: 700;
      color: var(--grey-dark);
    }

    .article-info {
      display: flex;
      font-weight: 400;

      div {
        display: flex;
        align-items: center;

        svg {
          margin-right: 4px;
        }
      }

      div:not(:last-child) {
        margin-right: 4px;
      }
    }
  }
`;

const ArticleCard = ({
  articleId,
  isCompleted,
  title,
  startDay,
  endDay,
  frontend,
  backend,
  hashtags,
  skills,
  memberName,
  heartCount,
  views,
}) => {
  return (
    <ArticleCardWrapper
      to={`/articles/${articleId}`}
      className={isCompleted === false ? '' : 'closed'}
    >
      <article>
        <h3 className="article-header">{title}</h3>
        <div className="project-info">
          <p>
            <span>개발 기간</span>
            {yearMonthDate(startDay)} - {yearMonthDate(endDay)}
          </p>
          <p className="people-number">
            <span>프론트엔드</span>
            {frontend}명<span>백엔드</span>
            {backend}명
          </p>
        </div>
        <div className="project-summary">
          <ul className="hashtag-list">
            {hashtags.map((tag) => (
              <li key={tag.hashtagId}>{'#' + tag.name}</li>
            ))}
          </ul>
          <ul className="skill-list">
            {skills.map((skill) => (
              <li key={skill.skillId}>
                <span>{skill.name}</span>
              </li>
            ))}
          </ul>
        </div>
        <footer>
          <div className="author">{memberName}</div>
          <div className="article-info">
            <div>
              <LikeIcon aria-label="좋아요 수" role="img" />
              <span>{heartCount}</span>
            </div>
            <div>
              <ViewsIcon aria-label="조회수" role="img" />
              <span>{views}</span>
            </div>
          </div>
        </footer>
      </article>
    </ArticleCardWrapper>
  );
};

export default ArticleCard;

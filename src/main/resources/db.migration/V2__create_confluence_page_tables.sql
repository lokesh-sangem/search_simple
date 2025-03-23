-- Create the main table
CREATE TABLE confluence_page_data (
    unique_id UUID PRIMARY KEY,
    id VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(300) NOT NULL,
    title VARCHAR(100) NOT NULL,
    content VARCHAR(300) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT chk_title_length CHECK (LENGTH(title) BETWEEN 10 AND 100),
    CONSTRAINT chk_description_length CHECK (LENGTH(description) BETWEEN 10 AND 300),
    CONSTRAINT chk_content_length CHECK (LENGTH(content) BETWEEN 10 AND 300)
);

-- Create table for storing tags related to a confluence page
CREATE TABLE confluence_page_tags (
    tag_id UUID PRIMARY KEY,
    tag VARCHAR(50) NOT NULL,
    confluence_page_data_unique_id UUID NOT NULL,
    FOREIGN KEY (confluence_page_data_unique_id) REFERENCES confluence_page_data(unique_id) ON DELETE CASCADE
);

-- Create table for storing links related to a confluence page
CREATE TABLE confluence_page_links (
    link_id UUID PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    id VARCHAR(50) NOT NULL,
    title VARCHAR(100) NOT NULL,
    reporter VARCHAR(100) NOT NULL,
    confluence_page_data_unique_id UUID NOT NULL,
    FOREIGN KEY (confluence_page_data_unique_id) REFERENCES confluence_page_data(unique_id) ON DELETE CASCADE
);